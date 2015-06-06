package ophelia.generator;

import org.jetbrains.annotations.NotNull;

/**
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
