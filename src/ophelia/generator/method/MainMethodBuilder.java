package ophelia.generator.method;

import ophelia.generator.WithImportBuilder;
import ophelia.generator.annotation.AnnotationWrapper;
import org.jetbrains.annotations.NotNull;

public interface MainMethodBuilder extends WithImportBuilder<MainMethodBuilder> {

	MainMethodBuilder withParameter(@NotNull Class<?> type, @NotNull String name);

	MainMethodBuilder withAnnotatedParameter(
			@NotNull AnnotationWrapper annotation,
			@NotNull Class<?> type,
			@NotNull String name
	);

	MethodWrapper build();
}
