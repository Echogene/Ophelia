package ophelia.generator.method;

import ophelia.generator.WithImportBuilder;
import ophelia.generator.method.parameter.ParameterWrapper;
import org.jetbrains.annotations.NotNull;

public interface MainMethodBuilder extends WithImportBuilder<MainMethodBuilder> {

	MainMethodBuilder withParameter(@NotNull ParameterWrapper parameter);

	MethodWrapper build();
}
