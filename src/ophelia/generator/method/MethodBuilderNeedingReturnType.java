package ophelia.generator.method;

import ophelia.generator.type.TypeBuilder;
import ophelia.generator.type.TypeWrapper;
import org.jetbrains.annotations.NotNull;

public interface MethodBuilderNeedingReturnType {

	@NotNull MainMethodBuilder withReturnType(@NotNull TypeWrapper type);

	@NotNull default MainMethodBuilder withReturnType(@NotNull Class<?> type) {
		return withReturnType(type.getCanonicalName());
	}

	@NotNull default MainMethodBuilder withReturnType(@NotNull String canonicalName) {
		return withReturnType(new TypeBuilder(canonicalName).build());
	}

	@NotNull MainMethodBuilder withVoidType();
}
