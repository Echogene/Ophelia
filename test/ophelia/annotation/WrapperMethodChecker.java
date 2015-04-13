package ophelia.annotation;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.lang.reflect.Field;
import java.util.List;

import static java.text.MessageFormat.format;
import static ophelia.util.CollectionUtils.first;
import static ophelia.util.CollectionUtils.subListOfClass;
import static ophelia.util.function.FunctionUtils.image;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * @author Steven Weston
 */
public class WrapperMethodChecker {

	private final Class<?> wrapper;
	private final Field wrappeeField;

	public WrapperMethodChecker(Class<?> wrapper, Field wrappeeField) {

		this.wrapper = wrapper;
		this.wrappeeField = wrappeeField;
	}

	public void checkMethod(MethodDeclaration method) {

		List<AnnotationExpr> annotations = method.getAnnotations();
		assertThat(
				format("Method\n{0}\nin {1} should override something", method, wrapper),
				image(annotations, a -> a.getName().getName()),
				hasItems("Override")
		);

		List<BlockStmt> methodStatements = subListOfClass(method.getChildrenNodes(), BlockStmt.class);
		assertThat(
				format("Method\n{0}\nin {1} should have one block", method, wrapper),
				methodStatements,
				hasSize(1)
		);

		BlockStmt block = first(methodStatements);
		List<Statement> statements = block.getStmts();
		assertThat(
				format("Method\n{0}\nin {1} should have one line", method, wrapper),
				statements,
				hasSize(1)
		);
		Statement statement = first(statements);

		statement.accept(new MethodVisitor(), null);
		assertThat(statement, is(anyOf(instanceOf(ExpressionStmt.class), instanceOf(ReturnStmt.class))));
	}

	private static class MethodVisitor extends VoidVisitorAdapter {
		@Override
		public void visit(ExpressionStmt n, Object arg) {
			assertThat(n.getExpression(), is(instanceOf(MethodCallExpr.class)));
			n.getExpression().accept(
					new VoidVisitorAdapter() {
						@Override
						public void visit(MethodCallExpr n, Object arg) {
						}
					}, null
			);
		}

		@Override
		public void visit(ReturnStmt n, Object arg) {


		}
	}
}
