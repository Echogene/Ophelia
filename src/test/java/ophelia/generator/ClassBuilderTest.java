package ophelia.generator;

import ophelia.generator.constructor.ConstructorBuilder;
import ophelia.generator.field.FieldBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

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
				.withConstructor(
						new ConstructorBuilder()
								.withProtection()
								.build()
				)
				.withField(
						new FieldBuilder("INSTANCE")
								.withType("ophelia.generator.TestImpl")
								.withInitialisation("new TestImpl() {}")
								.withAnnotation(NotNull.class)
								.withPublicity()
								.withStasis()
								.build()
				)
				.withSetMember("setMember", TestInterface.class)
				.withListMember("listMember", TestSuperClass.class)
				.withMapMember("mapMember", TestSuperClass.class, TestInterface.class)
				.writeToFile("src/test/java/ophelia/generator/TestImpl.java.test");
	}
}