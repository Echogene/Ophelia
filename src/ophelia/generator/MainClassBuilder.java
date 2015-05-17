package ophelia.generator;

import ophelia.generator.method.MethodWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author Steven Weston
 */
public interface MainClassBuilder extends WithImportBuilder<MainClassBuilder> {

	@NotNull MainClassBuilder withExtends(@NotNull Class<?> clazz);

	@NotNull MainClassBuilder withImplements(@NotNull Class<?> clazz);

	@NotNull MainClassBuilder withMethod(@NotNull MethodWrapper method);

	void writeToFile(File file);
}
