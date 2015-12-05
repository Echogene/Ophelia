package ophelia.generator;

import ophelia.builder.Builder;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * A builder that has can import some strings (generally package names).
 * @author Steven Weston
 */
public interface WithImportBuilder<T, B extends WithImportBuilder<T, B>> extends Builder<T, B> {

	@NotNull B withImport(@NotNull String canonicalClassName);

	@NotNull
	default B withImport(@NotNull Class<?> classToImport) {
		return withImport(classToImport.getCanonicalName());
	}

	@NotNull
	default B withImports(@NotNull Stream<String> canonicalClassNames) {
		return canonicalClassNames.map(this::withImport)
				.reduce((a, b) -> b)
				.orElse(noöp());
	}

	@NotNull
	default B withImports(@NotNull Collection<String> canonicalClassNames) {
		return withImports(canonicalClassNames.stream());
	}

	@NotNull
	default B withImports(@NotNull BaseCollection<String> canonicalClassNames) {
		return withImports(canonicalClassNames.stream());
	}
}
