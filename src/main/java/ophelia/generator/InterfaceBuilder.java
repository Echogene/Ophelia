package ophelia.generator;

import org.jetbrains.annotations.NotNull;

/**
 * Generate code for a Java class with the builder pattern.
 * @author Steven Weston
 */
public class InterfaceBuilder implements ClassBuilderNeedingPackageName {

	private final String className;

	public InterfaceBuilder(@NotNull String className) {
		this.className = className;
	}

	@NotNull
	@Override
	public MainClassBuilder withPackage(@NotNull String packageName) {
		return new BaseClassBuilder(packageName, className, true);
	}
}
