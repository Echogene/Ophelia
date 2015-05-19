package ophelia.generator;

import com.github.javaparser.ast.CompilationUnit;
import ophelia.generator.method.MethodWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Steven Weston
 */
public interface MainClassBuilder extends WithImportBuilder<MainClassBuilder, CompilationUnit> {

	@NotNull MainClassBuilder withExtends(@NotNull Class<?> clazz);

	@NotNull MainClassBuilder withImplements(@NotNull Class<?> clazz);

	@NotNull MainClassBuilder withMethod(@NotNull MethodWrapper method);

	default void writeToFile(File file) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF_8))) {

			writer.write(build().toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
