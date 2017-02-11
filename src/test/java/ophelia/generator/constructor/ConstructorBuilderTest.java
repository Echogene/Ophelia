package ophelia.generator.constructor;

import ophelia.generator.annotation.AnnotationBuilder;
import ophelia.generator.method.parameter.ParameterBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static ophelia.collections.matchers.IsEmptyCollection.empty;
import static ophelia.collections.matchers.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static ophelia.generator.expression.ExpressionBuilder.e;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;

/**
 * @author steven
 */
public class ConstructorBuilderTest {

	@Test
	public void test_annotation() throws Exception {
		ConstructorWrapper test = new ConstructorBuilder()
				.withAnnotation(new AnnotationBuilder(Override.class).build())
				.withAnnotation(new AnnotationBuilder(NotNull.class).build())
				.build();
		test.setClass("Test");

		assertThat(test.getNode().toString(), equalToIgnoringWhiteSpace("@Override\n@NotNull\npublic Test() {\n}"));

		assertThat(test.getImports(), containsInAnyOrder(
				Override.class.getCanonicalName(),
				NotNull.class.getCanonicalName()
		));
	}

	@Test
	public void test_privacy() throws Exception {
		ConstructorWrapper test = new ConstructorBuilder()
				.withPrivacy()
				.build();
		test.setClass("Test");

		assertThat(test.getNode().toString(), equalToIgnoringWhiteSpace("private Test() {\n}"));

		assertThat(test.getImports(), is(empty()));
	}

	@Test
	public void test_protection() throws Exception {
		ConstructorWrapper test = new ConstructorBuilder()
				.withProtection()
				.build();
		test.setClass("Test");

		assertThat(test.getNode().toString(), equalToIgnoringWhiteSpace("protected Test() {\n}"));

		assertThat(test.getImports(), is(empty()));
	}

	@Test
	public void test_no_privacy() throws Exception {
		ConstructorWrapper test = new ConstructorBuilder()
				.withNoPrivacyModifier()
				.build();
		test.setClass("Test");

		assertThat(test.getNode().toString(), equalToIgnoringWhiteSpace("Test() {\n}"));

		assertThat(test.getImports(), is(empty()));
	}

	@Test
	public void test_body_build() throws Exception {
		ConstructorWrapper test = new ConstructorBuilder()
				.withParameter(new ParameterBuilder("test").withType(ConstructorBuilder.class).build())
				.withStatement("this.test = test;")
				.build();
		test.setClass("Test");

		assertThat(test.getNode().toString(), equalToIgnoringWhiteSpace(
				"public Test(final ConstructorBuilder test) {\n    this.test = test;\n}"
		));

		assertThat(test.getImports(), containsInAnyOrder(
				ConstructorBuilder.class.getCanonicalName()
		));
	}

	@Test
	public void test_super_call_build() throws Exception {
		ConstructorWrapper test = new ConstructorBuilder()
				.withParameter(new ParameterBuilder("test").withType(ConstructorBuilder.class).build())
				.withSuperCall(e("test"))
				.build();
		test.setClass("Test");

		assertThat(test.getNode().toString(), equalToIgnoringWhiteSpace(
				"public Test(final ConstructorBuilder test) {\n    super(test);\n}"
		));

		assertThat(test.getImports(), containsInAnyOrder(
				ConstructorBuilder.class.getCanonicalName()
		));
	}
	@Test
	public void test_super_call_and_body_build() throws Exception {
		ConstructorWrapper test = new ConstructorBuilder()
				.withParameter(new ParameterBuilder("test").withType(ConstructorBuilder.class).build())
				.withSuperCall(e("test"))
				.withStatement("this.test = test;")
				.build();
		test.setClass("Test");

		assertThat(test.getNode().toString(), equalToIgnoringWhiteSpace(
				"public Test(final ConstructorBuilder test) {\n    super(test);\n    this.test = test;\n}"
		));

		assertThat(test.getImports(), containsInAnyOrder(
				ConstructorBuilder.class.getCanonicalName()
		));
	}
}