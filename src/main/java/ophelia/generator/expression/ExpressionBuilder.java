package ophelia.generator.expression;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.Expression;
import org.jetbrains.annotations.NotNull;

/**
 * @author steven
 */
public class ExpressionBuilder implements ExpressionBuilderNeedingImplementation {
	@NotNull
	@Override
	public MainExpressionBuilder withImplementation(@NotNull String expression) throws ParseException {
		return withImplementation(JavaParser.parseExpression(expression));
	}

	@NotNull
	@Override
	public MainExpressionBuilder withImplementation(@NotNull Expression expression) {
		return new BaseExpressionBuilder(expression);
	}

	/**
	 * A quick shortcut to an expression wrapper without any imports or anything.
	 */
	public static ExpressionWrapper e(String expression) throws ParseException {
		return new ExpressionBuilder()
				.withImplementation(expression)
				.build();
	}
}
