package ophelia.generator.method.parameter;

import ophelia.generator.annotation.AnnotationBuilder;
import ophelia.generator.type.TypeBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class ParameterBuilderTest {

	@Test
	public void test_build() throws Exception {
		ParameterWrapper parameter = new ParameterBuilder("test")
				.withType(
						new TypeBuilder(ParameterBuilderTest.class)
								.withAnnotation(new AnnotationBuilder(NotNull.class).build())
								.build()
				)
				.build();

		assertThat(parameter.getNode().toString(), is("@NotNull ParameterBuilderTest test"));

		assertThat(parameter.getImports().getUnmodifiableInnerSet(), containsInAnyOrder(
				NotNull.class.getCanonicalName(),
				ParameterBuilderTest.class.getCanonicalName()
		));
	}
}