package ophelia.util.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import ophelia.collections.set.StandardSet;
import ophelia.util.ClassUtils;
import ophelia.util.function.FunctionUtils;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.IntStream.range;
import static ophelia.exceptions.maybe.Maybe.wrapOutput;
import static ophelia.util.CollectionUtils.first;
import static ophelia.util.CollectionUtils.subListOfClass;
import static ophelia.util.javaparser.JavaParserReflector.findClass;
import static ophelia.util.javaparser.JavaParserReflector.findMethod;
import static ophelia.util.javaparser.SourceFinder.findSourceFile;
import static ophelia.util.javaparser.SourceFinder.parseSource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Steven Weston
 */
public class JavaParserReflectorTest {

	@Test
	public void test_can_find_classes() throws Exception {

		List<Class<?>> classes = Arrays.asList(
				JavaParserReflector.class,
				JavaParserReflectorTest.class,
				StandardSet.class,
				ClassUtils.class
		);
		for (Class<?> clazz : classes) {
			testClass(clazz);
		}
	}

	private void testClass(Class<?> clazz) throws Exception {

		File sourceFile = findSourceFile(clazz);
		CompilationUnit cu = parseSource(sourceFile);

		List<TypeDeclaration> types = cu.getTypes();
		List<ClassOrInterfaceDeclaration> declarations = subListOfClass(types, ClassOrInterfaceDeclaration.class);
		assertThat(declarations, hasSize(1));

		Class<?> foundClass = findClass(first(declarations));
		assertThat(foundClass, is(equalTo(clazz)));
	}

	@Test
	public void test_can_find_method() throws Exception {

		CompilationUnit cu = parseSource(findSourceFile(TestClass.class));

		List<TypeDeclaration> types = cu.getTypes();
		assertThat(types, hasSize(1));

		List<MethodDeclaration> methodDeclarations = subListOfClass(first(types).getMembers(), MethodDeclaration.class);

		List<Method> methods = Arrays.asList(
				TestClass.class.getMethod("test"),
				TestClass.class.getMethod("test", String.class),
				TestClass.class.getMethod("test", boolean.class),
				TestClass.class.getMethod("test", char.class),
				TestClass.class.getMethod("test", byte.class),
				TestClass.class.getMethod("test", short.class),
				TestClass.class.getMethod("test", int.class),
				TestClass.class.getMethod("test", long.class),
				TestClass.class.getMethod("test", float.class),
				TestClass.class.getMethod("test", double.class),
				TestClass.class.getMethod("test", Boolean.class),
				TestClass.class.getMethod("test", Character.class),
				TestClass.class.getMethod("test", Byte.class),
				TestClass.class.getMethod("test", Short.class),
				TestClass.class.getMethod("test", Integer.class),
				TestClass.class.getMethod("test", Long.class),
				TestClass.class.getMethod("test", Float.class),
				TestClass.class.getMethod("test", Double.class),
				TestClass.class.getMethod("test", TestClass.class),
				TestClass.class.getMethod("test", JavaParserReflector.class)
		);

		List<Method> foundMethods = FunctionUtils.image(
				methodDeclarations,
				wrapOutput((MethodDeclaration d) -> findMethod(d, TestClass.class))
						.andThen(maybeMethod -> maybeMethod.returnOnSuccess().nullOnFailure())
		);

		assertThat(foundMethods, hasSize(methods.size()));
		range(0, methods.size()).forEach(i -> assertThat(foundMethods.get(i), is(methods.get(i))));
	}
}