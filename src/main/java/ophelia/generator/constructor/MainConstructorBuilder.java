package ophelia.generator.constructor;

import com.github.javaparser.ParseException;
import ophelia.generator.WithImportBuilder;
import ophelia.generator.annotation.WithAnnotationBuilder;
import ophelia.generator.method.parameter.ParameterWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * @author steven
 */
public interface MainConstructorBuilder extends WithImportBuilder<ConstructorWrapper, MainConstructorBuilder>,
		WithAnnotationBuilder<MainConstructorBuilder> {

	@NotNull
	ConstructorBuilder withParameter(@NotNull ParameterWrapper parameter);

	@NotNull ConstructorBuilder withImplementation(@NotNull String implementation) throws ParseException;

	@NotNull ConstructorBuilder withPrivacy();

	@NotNull ConstructorBuilder withProtection();

	@NotNull ConstructorBuilder withNoPrivacyModifier();
}
