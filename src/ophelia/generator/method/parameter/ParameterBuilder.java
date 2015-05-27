package ophelia.generator.method.parameter;

import ophelia.generator.type.TypeWrapper;
import org.jetbrains.annotations.NotNull;

public class ParameterBuilder implements ParameterBuilderNeedingType {

	private final String name;

	public ParameterBuilder(@NotNull final String name) {
		this.name = name;
	}

	@NotNull
	@Override
	public MainParameterBuilder withType(@NotNull final TypeWrapper type) {
		return new BaseParameterBuilder(name, type);
	}
}
