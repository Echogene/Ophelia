package ophelia.generator;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.field.FieldBuilder;
import ophelia.generator.field.FieldWrapper;
import ophelia.generator.method.MethodBuilder;
import ophelia.generator.method.MethodWrapper;
import ophelia.generator.type.TypeBuilder;
import ophelia.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Steven Weston
 */
public interface MainClassBuilder extends WithImportBuilder<CompilationUnit, MainClassBuilder> {

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

	@NotNull MainClassBuilder withAbstraction();

	@NotNull
	default MainClassBuilder withSetMember(@NotNull String fieldName, @NotNull String canonicalMemberName) {
		try {
			return withField(
					new FieldBuilder(fieldName)
						.withType(
							new TypeBuilder(Set.class)
								.withGenericParameter(canonicalMemberName)
								.build()
						)
						.withInitialisation("new HashSet<>()")
						.withImport(HashSet.class)
						.build()
				)
				.withMethod(
					new MethodBuilder("get" + StringUtils.uppercaseFirst(fieldName))
						.withReturnType(
							new TypeBuilder(UnmodifiableSet.class)
								.withGenericParameter(canonicalMemberName)
								.build()
						)
						.withAnnotation(NotNull.class)
						.withImplementation("return new UnmodifiableSet<>(" + fieldName + ");")
						.build()
				);
		} catch (ParseException e) {
			throw new RuntimeException("This shouldn't happen because everything should be parseable", e);
		}
	}

	default MainClassBuilder withSetMember(@NotNull String fieldName, @NotNull Class<?> memberType) {
		return withSetMember(fieldName, memberType.getCanonicalName());
	}

	default void writeToFile(File file) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF_8))) {

			writer.write(build().toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
