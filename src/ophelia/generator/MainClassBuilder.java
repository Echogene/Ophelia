package ophelia.generator;

import java.io.File;

/**
 * @author Steven Weston
 */
public interface MainClassBuilder extends WithImportBuilder<MainClassBuilder> {

	MainClassBuilder withExtends(Class<?> clazz);

	MainClassBuilder withImplements(Class<?> clazz);

	void writeToFile(File file);
}
