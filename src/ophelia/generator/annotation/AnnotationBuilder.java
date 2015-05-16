package ophelia.generator.annotation;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.WithImportBuilder;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class AnnotationBuilder implements WithImportBuilder<AnnotationBuilder> {

	private final String annotationName;
	private final Set<String> imports = new HashSet<>();

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
