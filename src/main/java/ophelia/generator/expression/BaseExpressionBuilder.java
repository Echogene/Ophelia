package ophelia.generator.expression;

import com.github.javaparser.ast.expr.Expression;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author steven
 */
class BaseExpressionBuilder implements MainExpressionBuilder {

	private final Expression expression;
	private final Set<String> imports = new HashSet<>();

	public BaseExpressionBuilder(@NotNull Expression expression) {
		this.expression = expression;
	}

	@NotNull
	@Override
	public MainExpressionBuilder withImport(@NotNull String canonicalClassName) {
		imports.add(canonicalClassName);
		return this;
	}

	@NotNull
	@Override
	public ExpressionWrapper build() {
		return new ExpressionWrapper() {
			@NotNull
			@Override
			public UnmodifiableSet<String> getImports() {
				return new UnmodifiableSet<>(imports);
			}

			@NotNull
			@Override
			public Expression getNode() {
				return expression;
			}
		};
	}

	@NotNull
	@Override
	public MainExpressionBuilder noöp() {
		return this;
	}
}
