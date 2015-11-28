package ophelia.generator.relationship.onetomany;

import ophelia.generator.ClassBuilder;
import ophelia.generator.MainClassBuilder;
import org.junit.Test;

/**
 * @author Steven Weston
 */
public class OneToManyHierarchyBuilderTest {

	@Test
	public void test_build() throws Exception {

		MainClassBuilder parentBuilder = new ClassBuilder("GeneratedParent")
				.withPackage(OneToManyHierarchyBuilderTest.class.getPackage().getName());

		MainClassBuilder childBuilder = new ClassBuilder("GeneratedChild")
				.withPackage(OneToManyHierarchyBuilderTest.class.getPackage().getName());

		new OneToManyHierarchyBuilder(parentBuilder)
				.withChildBuilder(childBuilder)
				.build();

		parentBuilder.writeToFile("src/test/java/ophelia/generator/relationship/onetomany/GeneratedParent.java.test");
		childBuilder.writeToFile("src/test/java/ophelia/generator/relationship/onetomany/GeneratedChild.java.test");
	}

	@Test
	public void test_build_with_same_builder() throws Exception {

		MainClassBuilder builder = new ClassBuilder("GeneratedHierarchyMember")
				.withPackage(OneToManyHierarchyBuilderTest.class.getPackage().getName());

		new OneToManyHierarchyBuilder(builder)
				.withChildBuilder(builder)
				.build();

		builder.writeToFile("src/test/java/ophelia/generator/relationship/onetomany/GeneratedHierarchyMember.java");
	}
}