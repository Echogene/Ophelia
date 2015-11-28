package ophelia.generator.expression;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.expr.Expression;
import org.jetbrains.annotations.NotNull;

/**
 * @author steven
 */
public interface ExpressionBuilderNeedingImplementation {

	@NotNull
	MainExpressionBuilder withImplementation(@NotNull String expression) throws ParseException;

	@NotNull
	MainExpressionBuilder withImplementation(@NotNull Expression expression);
}
