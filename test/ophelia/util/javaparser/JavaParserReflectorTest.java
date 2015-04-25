package ophelia.util.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import ophelia.collections.set.StandardSet;
import ophelia.util.ClassUtils;
import ophelia.util.CollectionUtils;
import ophelia.util.TreeUtils;
import ophelia.util.function.ExceptionalFunction;
import ophelia.util.function.FunctionUtils;
import ophelia.util.javaparser.OtherTestClass.StaticNestedClass;
import ophelia.util.javaparser.OtherTestClass.StaticNestedClass.StaticDoubleNestedClass;
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
				getTestMethod(),
				getTestMethod(String.class),
				getTestMethod(boolean.class),
				getTestMethod(char.class),
				getTestMethod(byte.class),
				getTestMethod(short.class),
				getTestMethod(int.class),
				getTestMethod(long.class),
				getTestMethod(float.class),
				getTestMethod(double.class),
				getTestMethod(Boolean.class),
				getTestMethod(Character.class),
				getTestMethod(Byte.class),
				getTestMethod(Short.class),
				getTestMethod(Integer.class),
				getTestMethod(Long.class),
				getTestMethod(Float.class),
				getTestMethod(Double.class),
				getTestMethod(TestClass.class),
				getTestMethod(JavaParserReflector.class),
				getTestMethod(CollectionUtils.class),
				getTestMethod(ExceptionalFunction.class),
				getTestMethod(StaticNestedClass.class),
				getTestMethod(StaticDoubleNestedClass.class),
				getTestMethod(TestEnum.class),
				getTestMethod(TreeUtils.class),
				getTestMethod(String[].class),
				getTestMethod(boolean[].class),
				getTestMethod(char[].class),
				getTestMethod(byte[].class),
				getTestMethod(short[].class),
				getTestMethod(int[].class),
				getTestMethod(long[].class),
				getTestMethod(float[].class),
				getTestMethod(double[].class),
				getTestMethod(Boolean[].class)
		);

		List<Method> foundMethods = FunctionUtils.image(
				methodDeclarations,
				wrapOutput((MethodDeclaration d) -> findMethod(d, TestClass.class))
						.andThen(maybeMethod -> maybeMethod.returnOnSuccess().nullOnFailure())
		);

		assertThat(foundMethods, hasSize(methods.size()));
		range(0, methods.size()).forEach(i -> assertThat(foundMethods.get(i), is(methods.get(i))));
	}

	private Method getTestMethod(Class<?>... parameterTypes) throws NoSuchMethodException {
		return TestClass.class.getMethod("test", parameterTypes);
	}
}