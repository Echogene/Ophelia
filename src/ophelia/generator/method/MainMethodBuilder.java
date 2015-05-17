package ophelia.generator.method;

import com.github.javaparser.ParseException;
import ophelia.generator.WithImportBuilder;
import ophelia.generator.annotation.AnnotationWrapper;
import ophelia.generator.method.parameter.ParameterWrapper;
import org.jetbrains.annotations.NotNull;

public interface MainMethodBuilder extends WithImportBuilder<MainMethodBuilder> {

	@NotNull MainMethodBuilder withAnnotation(@NotNull AnnotationWrapper annotation);

	@NotNull MainMethodBuilder withParameter(@NotNull ParameterWrapper parameter);

	@NotNull MainMethodBuilder withImplementation(@NotNull String implementation) throws ParseException;

	@NotNull MainMethodBuilder withPrivacy();

	@NotNull MainMethodBuilder withProtection();

	@NotNull MainMethodBuilder withNoPrivacyModifier();

	@NotNull MainMethodBuilder withStasis();

	@NotNull MethodWrapper build();
}
