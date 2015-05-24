package ophelia.generator.method;

import com.github.javaparser.ParseException;
import ophelia.generator.WithImportBuilder;
import ophelia.generator.annotation.AnnotationBuilder;
import ophelia.generator.annotation.AnnotationWrapper;
import ophelia.generator.method.parameter.ParameterWrapper;
import org.jetbrains.annotations.NotNull;

public interface MainMethodBuilder extends WithImportBuilder<MethodWrapper, MainMethodBuilder> {

	@NotNull MainMethodBuilder withAnnotation(@NotNull AnnotationWrapper annotation);

	@NotNull
	default MainMethodBuilder withAnnotation(@NotNull String canonicalAnnotationName) {
		return withAnnotation(new AnnotationBuilder(canonicalAnnotationName).build());
	}

	@NotNull
	default MainMethodBuilder withAnnotation(@NotNull Class<?> annotationClass) {
		return withAnnotation(annotationClass.getCanonicalName());
	}

	@NotNull MainMethodBuilder withParameter(@NotNull ParameterWrapper parameter);

	@NotNull MainMethodBuilder withImplementation(@NotNull String implementation) throws ParseException;

	@NotNull MainMethodBuilder withPrivacy();

	@NotNull MainMethodBuilder withProtection();

	@NotNull MainMethodBuilder withNoPrivacyModifier();

	@NotNull MainMethodBuilder withStasis();
}
