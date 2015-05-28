package ophelia.generator.method;

import ophelia.generator.annotation.AnnotationBuilder;
import ophelia.generator.method.parameter.ParameterBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MethodBuilderTest {

	@Test
	public void test_build() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withReturnType(MethodBuilderTest.class)
				.withParameter(new ParameterBuilder("test")
								.withType(MethodBuilder.class)
								.build())
				.build();

		assertThat(test.getNode().toString(), is("public MethodBuilderTest test(final MethodBuilder test);"));

		assertThat(test.getImports().getUnmodifiableInnerSet(), containsInAnyOrder(
				MethodBuilderTest.class.getCanonicalName(),
				MethodBuilder.class.getCanonicalName()
		));
	}

	@Test
	public void test_annotation() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withVoidType()
				.withAnnotation(new AnnotationBuilder(Override.class).build())
				.withAnnotation(new AnnotationBuilder(NotNull.class).build())
				.build();

		assertThat(test.getNode().toString(), is("@Override\n@NotNull\npublic void test();"));

		assertThat(test.getImports().getUnmodifiableInnerSet(), containsInAnyOrder(
				Override.class.getCanonicalName(),
				NotNull.class.getCanonicalName()
		));
	}

	@Test
	public void test_stasis() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withVoidType()
				.withStasis()
				.build();

		assertThat(test.getNode().toString(), is("public static void test();"));

		assertThat(test.getImports().getUnmodifiableInnerSet(), is(empty()));
	}

	@Test
	public void test_privacy() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withVoidType()
				.withPrivacy()
				.build();

		assertThat(test.getNode().toString(), is("private void test();"));

		assertThat(test.getImports().getUnmodifiableInnerSet(), is(empty()));
	}

	@Test
	public void test_protection() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withVoidType()
				.withProtection()
				.build();

		assertThat(test.getNode().toString(), is("protected void test();"));

		assertThat(test.getImports().getUnmodifiableInnerSet(), is(empty()));
	}

	@Test
	public void test_no_privacy() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withVoidType()
				.withNoPrivacyModifier()
				.build();

		assertThat(test.getNode().toString(), is("void test();"));

		assertThat(test.getImports().getUnmodifiableInnerSet(), is(empty()));
	}

	@Test
	public void test_void_build() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withVoidType()
				.withParameter(new ParameterBuilder("test").withType(MethodBuilder.class).build())
				.withParameter(new ParameterBuilder("test2").withType(MethodBuilderTest.class).build())
				.build();

		assertThat(test.getNode().toString(), is("public void test(final MethodBuilder test, final MethodBuilderTest test2);"));

		assertThat(test.getImports().getUnmodifiableInnerSet(), containsInAnyOrder(
				MethodBuilderTest.class.getCanonicalName(),
				MethodBuilder.class.getCanonicalName()
		));
	}

	@Test
	public void test_body_build() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withReturnType(MethodBuilderTest.class)
				.withParameter(new ParameterBuilder("test").withType(MethodBuilder.class).build())
				.withImplementation("return null;")
				.build();

		assertThat(test.getNode().toString(), is("public MethodBuilderTest test(final MethodBuilder test) {\n    return null;\n}"));

		assertThat(test.getImports().getUnmodifiableInnerSet(), containsInAnyOrder(
				MethodBuilderTest.class.getCanonicalName(),
				MethodBuilder.class.getCanonicalName()
		));
	}

	@Test
	public void test_body_build_with_two_lines() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withReturnType(MethodBuilderTest.class)
				.withParameter(new ParameterBuilder("test").withType(MethodBuilder.class).build())
				.withImplementation(
						"MethodBuilderTest lol = new MethodBuilderTest();" +
								"return lol;"
				)
				.build();

		assertThat(test.getNode().toString(), is(equalToIgnoringWhiteSpace(
				"public MethodBuilderTest test(final MethodBuilder test) {\n" +
						"    MethodBuilderTest lol = new MethodBuilderTest();\n" +
						"    return lol;\n" +
						"}"
		)));

		assertThat(test.getImports().getUnmodifiableInnerSet(), containsInAnyOrder(
				MethodBuilderTest.class.getCanonicalName(),
				MethodBuilder.class.getCanonicalName()
		));
	}
}