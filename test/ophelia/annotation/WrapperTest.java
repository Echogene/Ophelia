package ophelia.annotation;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import ophelia.util.javaparser.SourceFinder;
import org.junit.Ignore;
import org.junit.Test;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.reflect.Modifier.*;
import static java.text.MessageFormat.format;
import static ophelia.util.CollectionUtils.first;
import static ophelia.util.CollectionUtils.subListOfClass;
import static ophelia.util.javaparser.SourceFinder.parseSource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.object.IsCompatibleType.typeCompatibleWith;

/**
 * A test that tests that wrappers only wrap classes.
 * @author Steven Weston
 */
public class WrapperTest {

	@Ignore
	@Test
	public void testWrappers() throws IOException, ParseException {
		Reflections reflections = new Reflections("ophelia");
		Set<Class<?>> wrapperClasses = reflections.getTypesAnnotatedWith(Wrapper.class);

		for (Class<?> wrapperClass : wrapperClasses) {

			File file = SourceFinder.findSourceFile(wrapperClass);
			CompilationUnit cu = parseSource(file);

			List<TypeDeclaration> types = cu.getTypes();
			assertThat(
					format("There should be only one type defined in {0}", file),
					types,
					hasSize(1)
			);
			checkClass(first(types), wrapperClass);
		}
	}

	private void checkClass(TypeDeclaration typeDeclaration, Class<?> wrapper) {

		assertThat(
				format("Wrapper {0} must be public", wrapper),
				typeDeclaration.getModifiers() & PUBLIC,
				is(PUBLIC)
		);

		Wrapper annotation = wrapper.getAnnotation(Wrapper.class);
		Class<?> wrappee = annotation.value();
		assertThat(
				format("Wrapper {0} must extend {1}", wrapper, wrappee),
				wrapper,
				is(typeCompatibleWith(wrappee))
		);

		List<Field> fields = Arrays.asList(wrapper.getDeclaredFields());
		List<Field> wrappeeFields = fields.stream()
				.filter(field -> field.getType().equals(wrappee))
				.collect(Collectors.toList());
		assertThat(
				format("Wrapper {0} must have a single field of type {1}", wrapper, wrappee),
				wrappeeFields,
				hasSize(1)
		);

		Field wrappeeField = first(wrappeeFields);
		int modifiers = wrappeeField.getModifiers();
		assertThat(modifiers & FINAL, is(FINAL));
		assertThat(modifiers & PRIVATE, is(PRIVATE));

		List<Constructor> constructors = Arrays.asList(wrapper.getConstructors());
		assertThat("Wrapper {0} must have a single constructor", constructors, hasSize(1));
		Constructor constructor = first(constructors);
		List<Class> parameterTypes = Arrays.asList(constructor.getParameterTypes());
		assertThat(parameterTypes, hasSize(1));
		assertThat(parameterTypes, contains(wrappee));

		List<MethodDeclaration> methodDeclarations = subListOfClass(
				typeDeclaration.getMembers(),
				MethodDeclaration.class
		);
		WrapperMethodChecker checker = new WrapperMethodChecker(wrapper, wrappeeField);
		methodDeclarations.forEach(checker::checkMethod);
	}

}
