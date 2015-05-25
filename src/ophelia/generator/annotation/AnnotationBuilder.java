package ophelia.generator.annotation;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.WithImportBuilder;
import ophelia.util.ClassUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class AnnotationBuilder implements WithImportBuilder<AnnotationWrapper, AnnotationBuilder> {

	private final String annotationName;
	private final Set<String> imports = new HashSet<>();

	public AnnotationBuilder(Class<? extends Annotation> annotationClass) {
		this(annotationClass.getCanonicalName());
	}

	public AnnotationBuilder(String canonicalClassName) {
		this.annotationName = ClassUtils.getSimpleName(canonicalClassName);
		this.withImport(canonicalClassName);
	}

	@NotNull
	@Override
	public AnnotationBuilder withImport(@NotNull String canonicalClassName) {
		imports.add(canonicalClassName);
		return this;
	}

	@NotNull
	@Override
	public AnnotationBuilder noöp() {
		return this;
	}

	@NotNull
	@Override
	public AnnotationWrapper build() {
		return new AnnotationWrapper() {
			@Override
			public AnnotationExpr getNode() {
				return new MarkerAnnotationExpr(new NameExpr(annotationName));
			}

			@NotNull
			@Override
			public UnmodifiableSet<String> getImports() {
				return new UnmodifiableSet<>(imports);
			}
		};
	}
}
