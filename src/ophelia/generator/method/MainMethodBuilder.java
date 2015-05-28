package ophelia.generator.method;

import com.github.javaparser.ParseException;
import ophelia.generator.WithImportBuilder;
import ophelia.generator.annotation.WithAnnotationBuilder;
import ophelia.generator.method.parameter.ParameterWrapper;
import org.jetbrains.annotations.NotNull;

public interface MainMethodBuilder
		extends WithImportBuilder<MethodWrapper, MainMethodBuilder>,
				WithAnnotationBuilder<MainMethodBuilder> {

	@NotNull MainMethodBuilder withParameter(@NotNull ParameterWrapper parameter);

	@NotNull MainMethodBuilder withImplementation(@NotNull String implementation) throws ParseException;

	@NotNull MainMethodBuilder withPrivacy();

	@NotNull MainMethodBuilder withProtection();

	@NotNull MainMethodBuilder withNoPrivacyModifier();

	@NotNull MainMethodBuilder withStasis();
}
