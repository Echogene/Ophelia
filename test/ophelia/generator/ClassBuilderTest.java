package ophelia.generator;

import ophelia.generator.field.FieldBuilder;
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
				.withAbstraction()
				.withField(
						new FieldBuilder("INSTANCE")
								.withType("ophelia.generator.TestImpl")
								.withInitialisation("new TestImpl() {}")
								.withPublicity()
								.withStasis()
								.build()
				)
				.withSetMember("setMember", TestInterface.class)
				.writeToFile(new File("test/ophelia/generator/TestImpl.java.test"));
	}
}