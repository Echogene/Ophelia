package ophelia.generator;

import java.util.stream.Stream;

/**
 * @author Steven Weston
 */
public interface WithImportBuilder<B extends WithImportBuilder> {

	B withImport(String canonicalClassName);

	default B withImport(Class<?> classToImport) {
		return withImport(classToImport.getCanonicalName());
	}

	default B withImports(Stream<String> canonicalClassNames) {
		return canonicalClassNames.map(this::withImport)
				.findAny()
				.orElse(null);
	}
}
