package ophelia.annotation;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import org.junit.Test;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.reflect.Modifier.*;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.text.MessageFormat.format;
import static ophelia.util.CollectionUtils.first;
import static ophelia.util.function.FunctionUtils.image;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.object.IsCompatibleType.typeCompatibleWith;

/**
 * A test that tests that wrappers only wrap classes.
 * @author Steven Weston
 */
public class WrapperTest {

	@Test
	public void testWrappers() throws IOException, ParseException {
		Reflections reflections = new Reflections("ophelia");
		Set<Class<?>> wrapperClasses = reflections.getTypesAnnotatedWith(Wrapper.class);
		for (Class<?> wrapperClass : wrapperClasses) {
			File file = getSourceFile(wrapperClass);
			CompilationUnit cu;
			try (FileInputStream fis = new FileInputStream(file)) {
				cu = JavaParser.parse(fis);
			}
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

		List<MethodDeclaration> methodDeclarations = typeDeclaration.getMembers().stream()
				.filter(member -> member instanceof MethodDeclaration)
				.map(member -> (MethodDeclaration) member)
				.collect(Collectors.toList());
		methodDeclarations.forEach(e -> checkMethod(e, wrapper));
	}

	private void checkMethod(MethodDeclaration method, Class<?> wrapper) {

		List<AnnotationExpr> annotations = method.getAnnotations();
		assertThat(
				format("Method\n{0}\nin {1} should override something", method, wrapper),
				image(annotations, a -> a.getName().getName()),
				hasItems("Override")
		);

		List<BlockStmt> methodStatements = method.getChildrenNodes().stream()
				.filter(child -> child instanceof BlockStmt)
				.map(child -> (BlockStmt) child)
				.collect(Collectors.toList());
		assertThat(
				format("Method\n{0}\nin {1} should have one block", method, wrapper),
				methodStatements,
				hasSize(1)
		);

		BlockStmt block = first(methodStatements);
		List<Statement> statements = block.getStmts();
		assertThat(
				format("Method\n{0}\nin {1} should have one line", method, wrapper),
				statements,
				hasSize(1)
		);
	}

	private File getSourceFile(Class clazz) throws IOException {
		String currentDirectoryName = System.getProperty("user.dir");
		Path currentDirectory = Paths.get(currentDirectoryName);
		String fileName = clazz.getSimpleName() + ".java";

		final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + "**/" + fileName);

		final List<File> matchedPaths = new ArrayList<>();
		Files.walkFileTree(
				currentDirectory, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

						if (file.getFileName() != null && matcher.matches(file)) {
							matchedPaths.add(new File(file.toUri()));
						}
						return CONTINUE;
					}
				}
		);
		if (matchedPaths.size() == 1) {
			return matchedPaths.get(0);
		} else {
			throw new IOException(format("Cannot find unique file for class {0}", clazz.getSimpleName()));
		}
	}
}
