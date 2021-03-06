package ophelia.generator.field;

import com.github.javaparser.ParseException;
import ophelia.generator.WithImportBuilder;
import ophelia.generator.annotation.WithAnnotationBuilder;
import org.jetbrains.annotations.NotNull;

public interface MainFieldBuilder
		extends WithImportBuilder<FieldWrapper, MainFieldBuilder>,
				WithAnnotationBuilder<MainFieldBuilder> {

	@NotNull MainFieldBuilder withPublicity();

	@NotNull MainFieldBuilder withProtection();

	@NotNull MainFieldBuilder withNoPrivacyModifier();

	@NotNull MainFieldBuilder withStasis();

	@NotNull MainFieldBuilder withNoFinality();

	@NotNull MainFieldBuilder withInitialisation(@NotNull String initialisation) throws ParseException;
}
