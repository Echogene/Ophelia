package ophelia.generator;

import java.io.File;

/**
 * @author Steven Weston
 */
public interface MainClassBuilder {

	MainClassBuilder withImport(String classToImport);

	MainClassBuilder withExtends(Class<?> clazz);

	MainClassBuilder withImplements(Class<?> clazz);

	void writeToFile(File file);
}
