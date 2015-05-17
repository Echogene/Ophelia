package ophelia.generator.method;

import com.github.javaparser.ParseException;
import ophelia.generator.WithImportBuilder;
import ophelia.generator.method.parameter.ParameterWrapper;
import org.jetbrains.annotations.NotNull;

public interface MainMethodBuilder extends WithImportBuilder<MainMethodBuilder> {

	MainMethodBuilder withParameter(@NotNull ParameterWrapper parameter);

	MainMethodBuilder withImplementation(String implementation) throws ParseException;

	MainMethodBuilder withPrivacy();

	MainMethodBuilder withProtection();

	MainMethodBuilder withNoPrivacyModifier();

	MethodWrapper build();
}
