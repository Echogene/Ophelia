package ophelia.generator.method;

import ophelia.map.UnmodifiableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class BaseMethodBuilder implements MainMethodBuilder {
	public BaseMethodBuilder(@NotNull String methodName, @Nullable Class<?> type) {
	}

	@Override
	public MainMethodBuilder withParameters(UnmodifiableMap<Class<?>, String> parameterNames) {
		// todo
		return this;
	}

	@Override
	public MethodWrapper build() {
		// todo
		return null;
	}
}
