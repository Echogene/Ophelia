package ophelia.generator.field;

import ophelia.generator.type.TypeBuilder;
import ophelia.generator.type.TypeWrapper;
import org.jetbrains.annotations.NotNull;

public interface FieldBuilderNeedingType {

	@NotNull MainFieldBuilder withType(@NotNull TypeWrapper type);

	@NotNull default MainFieldBuilder withType(@NotNull final Class<?> clazz) {
		return withType(clazz.getCanonicalName());
	}

	@NotNull default MainFieldBuilder withType(@NotNull final String canonicalTypeName) {
		return withType(new TypeBuilder(canonicalTypeName).build());
	}
}
