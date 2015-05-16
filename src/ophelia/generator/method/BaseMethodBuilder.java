package ophelia.generator.method;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.VoidType;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.method.parameter.ParameterWrapper;
import ophelia.util.ClassUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.reflect.Modifier.PUBLIC;

class BaseMethodBuilder implements MainMethodBuilder {

	private final String methodName;
	@Nullable private final String returnType;

	private final Set<String> imports = new HashSet<>();
	private final List<Parameter> parameters = new ArrayList<>();

	BaseMethodBuilder(@NotNull String methodName, @Nullable String canonicalTypeName) {
		this.methodName = methodName;
		this.returnType = ClassUtils.getSimpleName(canonicalTypeName);
		withImport(canonicalTypeName);
	}

	@Override
	public MainMethodBuilder withParameter(@NotNull ParameterWrapper parameter) {
		withImports(parameter.getImports().stream());
		parameters.add(parameter.getNode());
		return this;
	}

	@Override
	public MainMethodBuilder withImport(String canonicalClassName) {
		imports.add(canonicalClassName);
		return this;
	}

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
				MethodDeclaration declaration = new MethodDeclaration(PUBLIC, type, methodName);
				declaration.setParameters(parameters);
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
