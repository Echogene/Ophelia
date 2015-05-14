package ophelia.generator.annotation;

import ophelia.collections.list.UnmodifiableList;
import ophelia.generator.WithImportBuilder;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class AnnotationBuilder implements WithImportBuilder<AnnotationBuilder> {

	private final String annotationName;
	private final List<String> imports = new ArrayList<>();

	public AnnotationBuilder(Class<? extends Annotation> annotationClass) {
		this.annotationName = annotationClass.getSimpleName();
		this.withImport(annotationClass.getCanonicalName());
	}

	@Override
	public AnnotationBuilder withImport(String canonicalClassName) {
		imports.add(canonicalClassName);
		return this;
	}

	public AnnotationWrapper build() {
		return new AnnotationWrapper() {
			@Override
			public String getAnnotationName() {
				return annotationName;
			}

			@NotNull
			@Override
			public UnmodifiableList<String> getImports() {
				return new UnmodifiableList<>(imports);
			}
		};
	}
}
