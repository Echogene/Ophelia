package ophelia.annotation;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BaseParameter;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import ophelia.util.ListUtils;
import ophelia.util.StreamUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static java.text.MessageFormat.format;
import static ophelia.util.CollectionUtils.emptyIfNull;
import static ophelia.util.CollectionUtils.listOfClass;
import static ophelia.util.function.FunctionUtils.image;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
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

	public void checkMethod(@NotNull MethodDeclaration method) {

		List<AnnotationExpr> annotations = method.getAnnotations();

		if (image(annotations, a -> a.getName().getName()).contains("Override")) {
			checkOverriddenMethod(method);
		} else {
			checkUnwrappedMethod(method);
		}
	}

	private void checkUnwrappedMethod(@NotNull MethodDeclaration method) {

		// todo: check the unwrapped method is not evil
	}

	private void checkOverriddenMethod(@NotNull MethodDeclaration method) {

		List<BlockStmt> methodStatements = listOfClass(method.getChildrenNodes(), BlockStmt.class);
		assertThat(
				format("Method\n{0}\nin {1} should have one block", method, wrapper),
				methodStatements,
				hasSize(1)
		);

		BlockStmt block = ListUtils.first(methodStatements);
		assert block != null;
		List<Statement> statements = block.getStmts();
		assertThat(
				format("Method\n{0}\nin {1} should have one line", method, wrapper),
				statements,
				hasSize(1)
		);
		Statement statement = ListUtils.first(statements);

		assert statement != null;
		assertThat(
				format("Method\n{0}\nin {1} should return or call a void method", method, wrapper),
				statement,
				is(anyOf(instanceOf(ExpressionStmt.class), instanceOf(ReturnStmt.class)))
		);
		statement.accept(new MethodVisitor(), method);
	}

	private class MethodVisitor extends VoidVisitorAdapter<MethodDeclaration> {
		@Override
		public void visit(ExpressionStmt n, final MethodDeclaration method) {
			assertThat(
					format("Method\n{0}\nin {1} should call the wrappee", method, wrapper),
					n.getExpression(),
					is(instanceOf(MethodCallExpr.class))
			);
			n.getExpression().accept(methodCallChecker, method);
		}

		@Override
		public void visit(ReturnStmt n, final MethodDeclaration method) {

			Expression expr = n.getExpr();
			if (expr instanceof MethodCallExpr) {
				expr.accept(methodCallChecker, method);
			} else {
				assertThat(
						format("Method\n{0}\nin {1} should call the wrappee", method, wrapper),
						expr,
						is(instanceOf(ObjectCreationExpr.class))
				);
				// todo: check the constructed class is a wrapper and that its arguments are wrapped too
			}
		}
	}

	private final VoidVisitorAdapter<MethodDeclaration> nameChecker = new VoidVisitorAdapter<MethodDeclaration>() {
		@Override
		public void visit(NameExpr n, MethodDeclaration method) {
			assertThat(
					format(
							"Method\n{0}\nin {1} should call on ''{2}'', not ''{3}''",
							method,
							wrapper,
							wrappeeField.getName(),
							n.getName()
					),
					n.getName(),
					is(wrappeeField.getName())
			);
		}
	};

	private final VoidVisitorAdapter<MethodDeclaration> methodCallChecker = new VoidVisitorAdapter<MethodDeclaration>() {
		@Override
		public void visit(MethodCallExpr n, MethodDeclaration method) {
			assertThat(
					format("Method\n{0}\nin {1} should call the wrappee", method, wrapper),
					n.getScope(),
					is(instanceOf(NameExpr.class))
			);
			n.getScope().accept(nameChecker, method);

			assertThat(
					format(
							"Method\n{0}\nin {1} should call the method ''{2}'', not ''{3}''",
							method,
							wrapper,
							method.getName(),
							n.getName()
					),
					n.getName(),
					is(method.getName())
			);

			Collection<Expression> usedArguments = emptyIfNull(n.getArgs());
			Collection<Parameter> methodArguments = emptyIfNull(method.getParameters());

			assertThat(
					format(
							"Method\n{0}\nin {1} must use the same parameters when it calls the wrappee",
							method,
							wrapper
					),
					usedArguments,
					hasSize(methodArguments.size())
			);

			StreamUtils.consume(
					usedArguments.stream()
							.map(Node::toStringWithoutComments),
					methodArguments.stream()
							.map(BaseParameter::getId)
							.map(VariableDeclaratorId::getName),
					(s, t) -> assertThat(s, is(t))
			);
		}
	};
}
