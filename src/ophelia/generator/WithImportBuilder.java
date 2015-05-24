package ophelia.generator;

import ophelia.builder.Builder;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * @author Steven Weston
 */
public interface WithImportBuilder<T, B extends WithImportBuilder<T, B>> extends Builder<T, B> {

	@NotNull B withImport(String canonicalClassName);

	@NotNull
	default B withImport(Class<?> classToImport) {
		return withImport(classToImport.getCanonicalName());
	}

	@NotNull
	default B withImports(Stream<String> canonicalClassNames) {
		return canonicalClassNames.map(this::withImport)
				.reduce((a, b) -> b)
				.orElse(noöp());
	}
}
