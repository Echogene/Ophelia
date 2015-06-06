package ophelia.generator;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import ophelia.collections.list.UnmodifiableList;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.field.FieldBuilder;
import ophelia.generator.field.FieldWrapper;
import ophelia.generator.method.MethodBuilder;
import ophelia.generator.method.MethodWrapper;
import ophelia.generator.type.TypeBuilder;
import ophelia.map.UnmodifiableMap;
import ophelia.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

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

	@NotNull
	default MainClassBuilder withSetMember(@NotNull String fieldName, @NotNull Class<?> memberType) {
		return withSetMember(fieldName, memberType.getCanonicalName());
	}

	@NotNull
	default MainClassBuilder withListMember(@NotNull String fieldName, @NotNull String canonicalMemberName) {
		try {
			return withField(
					new FieldBuilder(fieldName)
						.withType(
							new TypeBuilder(List.class)
								.withGenericParameter(canonicalMemberName)
								.build()
						)
						.withInitialisation("new ArrayList<>()")
						.withImport(ArrayList.class)
						.build()
				)
				.withMethod(
					new MethodBuilder("get" + StringUtils.uppercaseFirst(fieldName))
						.withReturnType(
							new TypeBuilder(UnmodifiableList.class)
								.withGenericParameter(canonicalMemberName)
								.build()
						)
						.withAnnotation(NotNull.class)
						.withImplementation("return new UnmodifiableList<>(" + fieldName + ");")
						.build()
				);
		} catch (ParseException e) {
			throw new RuntimeException("This shouldn't happen because everything should be parseable", e);
		}
	}

	@NotNull
	default MainClassBuilder withListMember(@NotNull String fieldName, @NotNull Class<?> memberType) {
		return withListMember(fieldName, memberType.getCanonicalName());
	}

	@NotNull
	default MainClassBuilder withMapMember(
			@NotNull String fieldName,
			@NotNull String canonicalKeyName,
			@NotNull String canonicalValueName
	) {
		try {
			return withField(
					new FieldBuilder(fieldName)
						.withType(
							new TypeBuilder(Map.class)
								.withGenericParameter(canonicalKeyName)
								.withGenericParameter(canonicalValueName)
									.build()
						)
						.withInitialisation("new HashMap<>()")
						.withImport(HashMap.class)
						.build()
				)
				.withMethod(
					new MethodBuilder("get" + StringUtils.uppercaseFirst(fieldName))
						.withReturnType(
							new TypeBuilder(UnmodifiableMap.class)
								.withGenericParameter(canonicalKeyName)
								.withGenericParameter(canonicalValueName)
								.build()
						)
						.withAnnotation(NotNull.class)
						.withImplementation("return new UnmodifiableMap<>(" + fieldName + ");")
						.build()
				);
		} catch (ParseException e) {
			throw new RuntimeException("This shouldn't happen because everything should be parseable", e);
		}
	}

	@NotNull
	default MainClassBuilder withMapMember(
			@NotNull String fieldName,
			@NotNull Class<?> keyType,
			@NotNull Class<?> valueType
	) {
		return withMapMember(fieldName, keyType.getCanonicalName(), valueType.getCanonicalName());
	}

	@NotNull String getSimpleClassName();

	@NotNull String getCanonicalClassName();

	default void writeToFile(File file) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF_8))) {

			writer.write(build().toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
