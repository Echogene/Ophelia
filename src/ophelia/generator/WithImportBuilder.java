package ophelia.generator;

import ophelia.builder.Builder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

/**
 * @author Steven Weston
 */
public interface WithImportBuilder<B extends WithImportBuilder, T> extends Builder<T> {

	@NotNull B withImport(String canonicalClassName);

	@NotNull
	default B withImport(Class<?> classToImport) {
		return withImport(classToImport.getCanonicalName());
	}

	@Nullable
	default B withImports(Stream<String> canonicalClassNames) {
		return canonicalClassNames.map(this::withImport)
				.reduce((a, b) -> b)
				.orElse(null);
	}
}
