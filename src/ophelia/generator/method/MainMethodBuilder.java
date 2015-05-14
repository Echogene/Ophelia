package ophelia.generator.method;

import ophelia.generator.WithImportBuilder;
import ophelia.generator.annotation.AnnotationWrapper;

public interface MainMethodBuilder extends WithImportBuilder<MainMethodBuilder> {

	MainMethodBuilder withParameter(Class<?> type, String name);

	MainMethodBuilder withAnnotatedParameter(AnnotationWrapper annotation, Class<?> type, String name);

	MethodWrapper build();
}
