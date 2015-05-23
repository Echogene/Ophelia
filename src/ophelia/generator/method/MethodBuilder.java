package ophelia.generator.method;

import ophelia.generator.type.TypeWrapper;
import org.jetbrains.annotations.NotNull;

public class MethodBuilder implements MethodBuilderNeedingReturnType {

	private final String methodName;

	public MethodBuilder(@NotNull String methodName) {
		this.methodName = methodName;
	}

	@NotNull
	@Override
	public MainMethodBuilder withReturnType(@NotNull TypeWrapper type) {
		return new BaseMethodBuilder(methodName, type);
	}

	@NotNull
	@Override
	public MainMethodBuilder withVoidType() {
		return new BaseMethodBuilder(methodName, null);
	}
}
