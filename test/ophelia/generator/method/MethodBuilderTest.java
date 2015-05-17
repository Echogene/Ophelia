package ophelia.generator.method;

import ophelia.generator.method.parameter.ParameterBuilder;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MethodBuilderTest {

	@Test
	public void test_build() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withReturnType(MethodBuilderTest.class)
				.withParameter(new ParameterBuilder(MethodBuilder.class, "test").build())
				.build();

		assertThat(test.getNode().toString(), is("public MethodBuilderTest test(MethodBuilder test);"));

		assertThat(test.getImports().getUnmodifiableInnerSet(), containsInAnyOrder(
				MethodBuilderTest.class.getCanonicalName(),
				MethodBuilder.class.getCanonicalName()
		));
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
				.withParameter(new ParameterBuilder(MethodBuilder.class, "test").build())
				.withParameter(new ParameterBuilder(MethodBuilderTest.class, "test2").build())
				.build();

		assertThat(test.getNode().toString(), is("public void test(MethodBuilder test, MethodBuilderTest test2);"));

		assertThat(test.getImports().getUnmodifiableInnerSet(), containsInAnyOrder(
				MethodBuilderTest.class.getCanonicalName(),
				MethodBuilder.class.getCanonicalName()
		));
	}

	@Test
	public void test_body_build() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withReturnType(MethodBuilderTest.class)
				.withParameter(new ParameterBuilder(MethodBuilder.class, "test").build())
				.withImplementation("return null;")
				.build();

		assertThat(test.getNode().toString(), is("public MethodBuilderTest test(MethodBuilder test) {\n    return null;\n}"));

		assertThat(test.getImports().getUnmodifiableInnerSet(), containsInAnyOrder(
				MethodBuilderTest.class.getCanonicalName(),
				MethodBuilder.class.getCanonicalName()
		));
	}

	@Test
	public void test_body_build_with_two_lines() throws Exception {
		MethodWrapper test = new MethodBuilder("test")
				.withReturnType(MethodBuilderTest.class)
				.withParameter(new ParameterBuilder(MethodBuilder.class, "test").build())
				.withImplementation(
						"MethodBuilderTest lol = new MethodBuilderTest();" +
						"return lol;")
				.build();

		assertThat(test.getNode().toString(), is(equalToIgnoringWhiteSpace(
				"public MethodBuilderTest test(MethodBuilder test) {\n" +
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