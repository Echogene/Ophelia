package ophelia.generator.type;

import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class TypeBuilderTest {

	@Test
	public void test_build() throws Exception {
		TypeWrapper test = new TypeBuilder(Set.class)
				.withGenericParameter(TypeBuilderTest.class)
				.build();

		assertThat(test.getNode().toString(), is("Set<TypeBuilderTest>"));

		assertThat(
				test.getImports().getUnmodifiableInnerSet(),
				containsInAnyOrder(
						Set.class.getCanonicalName(),
						TypeBuilderTest.class.getCanonicalName()
				)
		);
	}

	@Test
	public void test_build_with_multiple_generics() throws Exception {
		TypeWrapper test = new TypeBuilder(Map.class)
				.withGenericParameter(TypeBuilder.class)
				.withGenericParameter(TypeBuilderTest.class)
				.build();

		assertThat(test.getNode().toString(), is("Map<TypeBuilder, TypeBuilderTest>"));

		assertThat(
				test.getImports().getUnmodifiableInnerSet(),
				containsInAnyOrder(
						Map.class.getCanonicalName(),
						TypeBuilder.class.getCanonicalName(),
						TypeBuilderTest.class.getCanonicalName()
				)
		);
	}
}