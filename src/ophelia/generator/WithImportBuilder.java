package ophelia.generator;

import ophelia.builder.Builder;

import java.util.stream.Stream;

/**
 * @author Steven Weston
 */
public interface WithImportBuilder<B extends WithImportBuilder, T> extends Builder<T> {

	B withImport(String canonicalClassName);

	default B withImport(Class<?> classToImport) {
		return withImport(classToImport.getCanonicalName());
	}

	default B withImports(Stream<String> canonicalClassNames) {
		return canonicalClassNames.map(this::withImport)
				.reduce((a, b) -> b)
				.orElse(null);
	}
}
