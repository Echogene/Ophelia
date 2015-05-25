package ophelia.generator.field;

import com.github.javaparser.ParseException;
import ophelia.generator.WithImportBuilder;
import ophelia.generator.annotation.AnnotationBuilder;
import ophelia.generator.annotation.AnnotationWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public interface MainFieldBuilder extends WithImportBuilder<FieldWrapper, MainFieldBuilder> {

	@NotNull MainFieldBuilder withPublicity();

	@NotNull MainFieldBuilder withProtection();

	@NotNull MainFieldBuilder withNoPrivacyModifier();

	@NotNull MainFieldBuilder withStasis();

	@NotNull MainFieldBuilder withNoFinality();

	@NotNull MainFieldBuilder withInitialisation(@NotNull String initialisation) throws ParseException;

	@NotNull MainFieldBuilder withAnnotation(@NotNull AnnotationWrapper annotation);

	@NotNull
	default MainFieldBuilder withAnnotation(@NotNull String canonicalAnnotationName) {
		return withAnnotation(new AnnotationBuilder(canonicalAnnotationName).build());
	}

	@NotNull
	default MainFieldBuilder withAnnotation(@NotNull Class<? extends Annotation> canonicalAnnotationClass) {
		return withAnnotation(canonicalAnnotationClass.getCanonicalName());
	}
}
