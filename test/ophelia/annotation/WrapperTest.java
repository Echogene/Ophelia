package ophelia.annotation;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import org.junit.Test;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.text.MessageFormat.format;
import static ophelia.util.CollectionUtils.first;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

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
			assertThat(cu.getTypes(), hasSize(1));
			checkClass(first(cu.getTypes()), wrapperClass.getAnnotation(Wrapper.class));
		}
	}

	private void checkClass(TypeDeclaration typeDeclaration, Wrapper annotation) {

		assertThat(typeDeclaration.getModifiers() & Modifier.PUBLIC, is(1));

		// todo: assert that the class extends the given value of the annotation
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
