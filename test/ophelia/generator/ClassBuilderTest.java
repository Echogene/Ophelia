package ophelia.generator;

import org.junit.Test;

import java.io.File;

/**
 * @author Steven Weston
 */
public class ClassBuilderTest {

	@Test
	public void testGeneration() throws Exception {

		new ClassBuilder("TestImpl")
				.withPackage("ophelia.generator")
				.withImplements(TestInterface.class)
				.withExtends(TestSuperClass.class)
				.writeToFile(new File("test/ophelia/generator/TestImpl.java.test"));
	}
}