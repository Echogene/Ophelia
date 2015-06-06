package ophelia.generator.method.parameter;

import ophelia.generator.type.TypeBuilder;
import ophelia.generator.type.TypeWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface ParameterBuilderNeedingType {

	@NotNull MainParameterBuilder withType(@NotNull final TypeWrapper type);

	@NotNull default MainParameterBuilder withType(@NotNull final Class<?> clazz) {
		return withType(clazz.getCanonicalName());
	}

	@NotNull default MainParameterBuilder withType(@NotNull final String canonicalTypeName) {
		return withType(new TypeBuilder(canonicalTypeName).build());
	}
}
