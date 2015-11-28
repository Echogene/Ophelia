package ophelia.generator.constructor;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.NodeWrapper;
import ophelia.generator.annotation.AnnotationWrapper;
import ophelia.generator.expression.ExpressionWrapper;
import ophelia.generator.method.parameter.ParameterWrapper;
import ophelia.util.ClassUtils;
import ophelia.util.FunctionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.reflect.Modifier.*;

/**
 * @author steven
 */
public class ConstructorBuilder implements MainConstructorBuilder {

	private final Set<String> imports = new HashSet<>();
	private final List<Parameter> parameters = new ArrayList<>();
	private Statement superCall;
	private List<Statement> statements = new ArrayList<>();
	private int modifiers = PUBLIC;
	private final List<AnnotationExpr> annotations = new ArrayList<>();


	@NotNull
	@Override
	public ConstructorBuilder withParameter(@NotNull ParameterWrapper parameter) {
		withImports(parameter.getImports());
		parameters.add(parameter.getNode());
		return this;
	}

	@NotNull
	@Override
	public ConstructorBuilder withSuperCall(@NotNull ExpressionWrapper... arguments) throws ParseException {
		List<Expression> args = FunctionUtils.image(arguments, NodeWrapper::getNode);
		// A hack follows because parsing "super(arg)" doesn't quite work in javaparser.
		MethodCallExpr superCall = new MethodCallExpr(null, "super", args);
		this.superCall = new ExpressionStmt(superCall);

		for (ExpressionWrapper argument : arguments) {
			withImports(argument.getImports());
		}
		return this;
	}

	@NotNull
	@Override
	public ConstructorBuilder withStatement(@NotNull String statement) throws ParseException {
		statements.add(JavaParser.parseStatement(statement));
		return this;
	}

	@NotNull
	@Override
	public ConstructorBuilder withPrivacy() {
		modifiers = (modifiers & ~PUBLIC & ~PROTECTED) | PRIVATE;
		return this;
	}

	@NotNull
	@Override
	public ConstructorBuilder withProtection() {
		modifiers = (modifiers & ~PUBLIC & ~PRIVATE) | PROTECTED;
		return this;
	}

	@NotNull
	@Override
	public ConstructorBuilder withNoPrivacyModifier() {
		modifiers = modifiers & ~PUBLIC & ~PRIVATE & ~PROTECTED;
		return this;
	}

	@NotNull
	@Override
	public ConstructorBuilder noöp() {
		return this;
	}

	@NotNull
	@Override
	public ConstructorBuilder withAnnotation(@NotNull AnnotationWrapper annotation) {
		annotations.add(annotation.getNode());
		withImports(annotation.getImports());
		return this;
	}

	@NotNull
	@Override
	public ConstructorBuilder withImport(@NotNull String canonicalClassName) {
		imports.add(canonicalClassName);
		return this;
	}

	@NotNull
	@Override
	public ConstructorWrapper build() {
		return new ConstructorWrapper() {
			public String className;

			@Override
			public void setClass(String className) {
				this.className = ClassUtils.getSimpleName(className);
			}

			@NotNull
			@Override
			public UnmodifiableSet<String> getImports() {
				return new UnmodifiableSet<>(imports);
			}

			@NotNull
			@Override
			public ConstructorDeclaration getNode() {
				if (className == null) {
					throw new RuntimeException("Name was not set for constructor");
				}
				ConstructorDeclaration declaration = new ConstructorDeclaration(modifiers, className);
				declaration.setAnnotations(annotations);
				declaration.setParameters(parameters);
				List<Statement> fullStatements = new ArrayList<>();
				if (superCall != null) {
					fullStatements.add(superCall);
				}
				fullStatements.addAll(statements);
				BlockStmt block = new BlockStmt(fullStatements);
				declaration.setBlock(block);
				return declaration;
			}
		};
	}
}
