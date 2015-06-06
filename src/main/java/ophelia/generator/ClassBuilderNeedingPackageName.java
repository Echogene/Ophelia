package ophelia.generator;

import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface ClassBuilderNeedingPackageName {

	@NotNull MainClassBuilder withPackage(@NotNull String packageName);
}
