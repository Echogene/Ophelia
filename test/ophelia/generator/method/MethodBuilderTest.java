package ophelia.generator.method;

import ophelia.generator.method.parameter.ParameterBuilder;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

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
}