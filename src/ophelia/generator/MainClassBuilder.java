package ophelia.generator;

/**
 * @author Steven Weston
 */
public interface MainClassBuilder {

	MainClassBuilder withImport(String classToImport);

	MainClassBuilder withExtends(Class<?> clazz);

	MainClassBuilder withImplements(Class<?> clazz);

	void generate();
}
