package ophelia.generator;

import com.github.javaparser.ast.CompilationUnit;
import ophelia.generator.field.FieldWrapper;
import ophelia.generator.method.MethodWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Steven Weston
 */
public interface MainClassBuilder extends WithImportBuilder<MainClassBuilder, CompilationUnit> {

	@NotNull MainClassBuilder withExtends(@NotNull String canonicalClassName);

	@NotNull default MainClassBuilder withExtends(@NotNull Class<?> clazz) {
		return withExtends(clazz.getCanonicalName());
	}

	@NotNull MainClassBuilder withImplements(@NotNull String canonicalClassName);

	@NotNull default MainClassBuilder withImplements(@NotNull Class<?> clazz) {
		return withImplements(clazz.getCanonicalName());
	}

	@NotNull MainClassBuilder withMethod(@NotNull MethodWrapper method);

	@NotNull MainClassBuilder withField(@NotNull FieldWrapper field);

	default void writeToFile(File file) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF_8))) {

			writer.write(build().toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@NotNull MainClassBuilder withAbstraction();
}
