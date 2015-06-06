package ophelia.generator.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

/**
 * @author Steven Weston
 */
public interface WithAnnotationBuilder<B extends WithAnnotationBuilder<B>> {

	@NotNull B withAnnotation(@NotNull AnnotationWrapper annotation);

	@NotNull
	default B withAnnotation(@NotNull String canonicalAnnotationName) {
		return withAnnotation(new AnnotationBuilder(canonicalAnnotationName).build());
	}

	@NotNull
	default B withAnnotation(@NotNull Class<? extends Annotation> annotation) {
		return withAnnotation(new AnnotationBuilder(annotation).build());
	}
}
