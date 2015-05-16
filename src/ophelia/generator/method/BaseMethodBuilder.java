package ophelia.generator.method;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.annotation.AnnotationWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class BaseMethodBuilder implements MainMethodBuilder {

	private final String methodName;

	private final Set<String> imports = new HashSet<>();
	private final List<Parameter> parameters = new ArrayList<>();

	public BaseMethodBuilder(@NotNull String methodName, @Nullable Class<?> type) {
		this.methodName = methodName;
	}

	@Override
	public MainMethodBuilder withParameter(@NotNull Class<?> type, @NotNull String name) {
		withImport(type);
		return this;
	}

	@Override
	public MainMethodBuilder withAnnotatedParameter(
			@NotNull AnnotationWrapper annotation,
			@NotNull Class<?> type,
			@NotNull String name
	) {
		withImport(type);
		withImports(annotation.getImports().stream());
		// todo
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
				MethodDeclaration declaration = new MethodDeclaration();
				declaration.setParameters(parameters);
				declaration.setName(methodName);
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
