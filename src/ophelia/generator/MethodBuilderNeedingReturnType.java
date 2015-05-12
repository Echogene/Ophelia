package ophelia.generator;

import org.jetbrains.annotations.NotNull;

public interface MethodBuilderNeedingReturnType {

	MainMethodBuilder withReturnType(@NotNull Class<?> type);

	MainMethodBuilder withVoidType();
}
