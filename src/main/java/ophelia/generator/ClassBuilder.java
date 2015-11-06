package ophelia.generator;

import org.jetbrains.annotations.NotNull;

/**
 * Generate code for a Java class with the builder pattern.
 * @author Steven Weston
 */
public class ClassBuilder implements ClassBuilderNeedingPackageName {

	private final String className;

	public ClassBuilder(@NotNull String className) {
		this.className = className;
	}

	@NotNull
	@Override
	public MainClassBuilder withPackage(@NotNull String packageName) {
		return new BaseClassBuilder(packageName, className);
	}
}
