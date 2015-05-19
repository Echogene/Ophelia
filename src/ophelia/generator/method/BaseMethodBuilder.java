package ophelia.generator.method;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.VoidType;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.annotation.AnnotationWrapper;
import ophelia.generator.method.parameter.ParameterWrapper;
import ophelia.util.ClassUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.reflect.Modifier.*;

class BaseMethodBuilder implements MainMethodBuilder {

	private final String methodName;
	@Nullable private final String returnType;

	private final Set<String> imports = new HashSet<>();
	private final List<Parameter> parameters = new ArrayList<>();
	private BlockStmt block;
	private int modifiers = PUBLIC;
	private final List<AnnotationExpr> annotations = new ArrayList<>();

	BaseMethodBuilder(@NotNull String methodName, @Nullable String canonicalTypeName) {
		this.methodName = methodName;
		if (canonicalTypeName == null) {
			returnType = null;
		} else {
			this.returnType = ClassUtils.getSimpleName(canonicalTypeName);
			withImport(canonicalTypeName);
		}
	}

	@NotNull
	@Override
	public MainMethodBuilder withAnnotation(@NotNull AnnotationWrapper annotation) {
		annotations.add(annotation.getNode());
		withImports(annotation.getImports().stream());
		return this;
	}

	@NotNull
	@Override
	public MainMethodBuilder withParameter(@NotNull ParameterWrapper parameter) {
		withImports(parameter.getImports().stream());
		parameters.add(parameter.getNode());
		return this;
	}

	@NotNull
	@Override
	public MainMethodBuilder withImplementation(@NotNull String implementation) throws ParseException {
		block = JavaParser.parseBlock("{" + implementation + "}");
		return this;
	}

	@NotNull
	@Override
	public MainMethodBuilder withPrivacy() {
		modifiers = (modifiers & ~PUBLIC & ~PROTECTED) | PRIVATE;
		return this;
	}

	@NotNull
	@Override
	public MainMethodBuilder withProtection() {
		modifiers = (modifiers & ~PUBLIC & ~PRIVATE) | PROTECTED;
		return this;
	}

	@NotNull
	@Override
	public MainMethodBuilder withNoPrivacyModifier() {
		modifiers = modifiers & ~PUBLIC & ~PRIVATE & ~PROTECTED;
		return this;
	}

	@NotNull
	@Override
	public MainMethodBuilder withStasis() {
		modifiers = modifiers | STATIC;
		return this;
	}

	@NotNull
	@Override
	public MainMethodBuilder withImport(String canonicalClassName) {
		imports.add(canonicalClassName);
		return this;
	}

	@NotNull
	@Override
	public MethodWrapper build() {
		return new MethodWrapper() {
			@Override
			public MethodDeclaration getNode() {
				Type type;
				if (returnType == null) {
					type = new VoidType();
				} else {
					type = new ClassOrInterfaceType(returnType);
				}
				MethodDeclaration declaration = new MethodDeclaration(modifiers, type, methodName);
				declaration.setParameters(parameters);
				declaration.setAnnotations(annotations);
				if (block != null) {
					declaration.setBody(block);
				}
				return declaration;
			}

			@NotNull
			@Override
			public UnmodifiableSet<String> getImports() {
				return new UnmodifiableSet<>(imports);
			}
		};
	}
}
