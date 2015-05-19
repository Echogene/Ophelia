package ophelia.generator.method.parameter;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.Type;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.type.TypeWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class BaseParameterBuilder implements MainParameterBuilder {

	private final Set<String> imports = new HashSet<>();

	private final List<AnnotationExpr> annotations = new ArrayList<>();
	private final String name;
	private final Type type;

	public BaseParameterBuilder(
			final String name,
			final TypeWrapper type
	) {

		this.name = name;
		this.type = type.getNode();
		withImports(type.getImports().stream());
	}

	@NotNull
	@Override
	public MainParameterBuilder withImport(String canonicalClassName) {
		imports.add(canonicalClassName);
		return this;
	}

	@Override
	public ParameterWrapper build() {
		return new ParameterWrapper() {
			@NotNull
			@Override
			public UnmodifiableSet<String> getImports() {
				return new UnmodifiableSet<>(imports);
			}

			@Override
			public Parameter getNode() {
				return new Parameter(type, new VariableDeclaratorId(name));
			}
		};
	}
}
