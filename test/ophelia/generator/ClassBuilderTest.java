package ophelia.generator;

import org.junit.Test;

import java.io.File;

/**
 * @author Steven Weston
 */
public class ClassBuilderTest {

	@Test
	public void testGeneration() throws Exception {

		new ClassBuilder(new File("test/ophelia/generator/TestImpl.java.test"))
				.withPackage("ophelia.generator")
				.withClassName("TestImpl")
				.generate();
	}
}