package ophelia.generator;

/**
 * @author Steven Weston
 */
public interface WithImportBuilder<B> {

	B withImport(String classToImport);

	default B withImport(Class<?> classToImport) {
		return withImport(classToImport.getCanonicalName());
	}
}
