package ophelia.generator.field;

import org.junit.Test;

import static ophelia.collections.matchers.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.is;

public class FieldBuilderTest {

	@Test
	public void test_build() throws Exception {
		FieldWrapper test = new FieldBuilder("test")
				.withType(FieldBuilderTest.class)
				.build();

		assertThat(test.getNode().toString(), is("private final FieldBuilderTest test;"));

		assertThat(test.getImports(), contains(FieldBuilderTest.class.getCanonicalName()));
	}

	@Test
	public void test_build_with_publicity() throws Exception {
		FieldWrapper test = new FieldBuilder("test")
				.withType(FieldBuilderTest.class)
				.withPublicity()
				.build();

		assertThat(test.getNode().toString(), is("public final FieldBuilderTest test;"));

		assertThat(test.getImports(), contains(FieldBuilderTest.class.getCanonicalName()));
	}

	@Test
	public void test_build_with_protection() throws Exception {
		FieldWrapper test = new FieldBuilder("test")
				.withType(FieldBuilderTest.class)
				.withProtection()
				.build();

		assertThat(test.getNode().toString(), is("protected final FieldBuilderTest test;"));

		assertThat(test.getImports(), contains(FieldBuilderTest.class.getCanonicalName()));
	}

	@Test
	public void test_build_with_no_privacy_modifier() throws Exception {
		FieldWrapper test = new FieldBuilder("test")
				.withType(FieldBuilderTest.class)
				.withNoPrivacyModifier()
				.build();

		assertThat(test.getNode().toString(), is("final FieldBuilderTest test;"));

		assertThat(test.getImports(), contains(FieldBuilderTest.class.getCanonicalName()));
	}

	@Test
	public void test_build_with_no_privacy_stasis() throws Exception {
		FieldWrapper test = new FieldBuilder("test")
				.withType(FieldBuilderTest.class)
				.withStasis()
				.build();

		assertThat(test.getNode().toString(), is("private static final FieldBuilderTest test;"));

		assertThat(test.getImports(), contains(FieldBuilderTest.class.getCanonicalName()));
	}

	@Test
	public void test_build_with_no_finality() throws Exception {
		FieldWrapper test = new FieldBuilder("test")
				.withType(FieldBuilderTest.class)
				.withNoFinality()
				.build();

		assertThat(test.getNode().toString(), is("private FieldBuilderTest test;"));

		assertThat(test.getImports(), contains(FieldBuilderTest.class.getCanonicalName()));
	}

	@Test
	public void test_build_with_initialisation() throws Exception {
		FieldWrapper test = new FieldBuilder("test")
				.withType(FieldBuilderTest.class)
				.withInitialisation("new FieldBuilderTest()")
				.build();

		assertThat(
				test.getNode().toString(),
				equalToIgnoringWhiteSpace("private final FieldBuilderTest test = new FieldBuilderTest();")
		);

		assertThat(test.getImports(), contains(FieldBuilderTest.class.getCanonicalName()));
	}
}