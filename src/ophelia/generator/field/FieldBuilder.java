package ophelia.generator.field;

import ophelia.generator.type.TypeWrapper;
import org.jetbrains.annotations.NotNull;

public class FieldBuilder implements FieldBuilderNeedingType {

	private final String fieldName;

	public FieldBuilder(@NotNull String fieldName) {
		this.fieldName = fieldName;
	}

	@NotNull
	@Override
	public MainFieldBuilder withType(TypeWrapper type) {
		return new BaseFieldBuilder(type, fieldName);
	}
}
