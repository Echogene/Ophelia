package ophelia.generator.method;

import org.jetbrains.annotations.NotNull;

public class MethodBuilder implements MethodBuilderNeedingReturnType {

	private final String methodName;

	public MethodBuilder(@NotNull String methodName) {
		this.methodName = methodName;
	}

	@Override
	public MainMethodBuilder withReturnType(@NotNull Class<?> type) {
		return new BaseMethodBuilder(methodName, type.getCanonicalName());
	}

	@Override
	public MainMethodBuilder withVoidType() {
		return new BaseMethodBuilder(methodName, null);
	}
}
