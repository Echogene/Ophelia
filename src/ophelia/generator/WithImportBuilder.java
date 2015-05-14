package ophelia.generator;

/**
 * @author Steven Weston
 */
public interface WithImportBuilder<B extends WithImportBuilder> {

	B withImport(String canonicalClassName);

	default B withImport(Class<?> classToImport) {
		return withImport(classToImport.getCanonicalName());
	}
}
