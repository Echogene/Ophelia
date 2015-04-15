package ophelia.util.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import ophelia.collections.set.StandardSet;
import ophelia.util.ClassUtils;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static ophelia.util.CollectionUtils.first;
import static ophelia.util.CollectionUtils.subListOfClass;
import static ophelia.util.javaparser.JavaParserReflector.findClass;
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
}