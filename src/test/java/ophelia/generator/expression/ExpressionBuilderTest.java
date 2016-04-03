package ophelia.generator.expression;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.core.Is.is;

/**
 * @author steven
 */
public class ExpressionBuilderTest {

	@Test
	public void test_build() throws Exception {
		ExpressionWrapper expressionWrapper = new ExpressionBuilder()
				.withImplementation("lol")
				.build();
		assertThat(expressionWrapper.getNode().toString(), is("lol"));
	}

	@Test
	public void test_imports() throws Exception {
		ExpressionWrapper expressionWrapper = new ExpressionBuilder()
				.withImplementation("new ExpressionBuilder()")
				.withImport(ExpressionBuilder.class)
				.build();
		assertThat(expressionWrapper.getNode().toString(), is(equalToIgnoringWhiteSpace("new ExpressionBuilder()")));
		assertThat(expressionWrapper.getImports().getUnmodifiableInnerSet(),
				containsInAnyOrder(ExpressionBuilder.class.getCanonicalName()));
	}
}