package ophelia.generator.method.parameter;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.WithImportBuilder;
import ophelia.generator.annotation.AnnotationWrapper;
import ophelia.util.ClassUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ParameterBuilder implements WithImportBuilder<ParameterBuilder> {

	private final String typeName;
	private final String name;
	private final Set<String> imports = new HashSet<>();
	private final List<AnnotationExpr> annotations = new ArrayList<>();

	public ParameterBuilder(Class<?> type, String name) {
		this(type.getCanonicalName(), name);
	}

	public ParameterBuilder(String canonicalTypeName, String name) {
		this.typeName = ClassUtils.getSimpleName(canonicalTypeName);
		this.name = name;
		withImport(canonicalTypeName);
	}

	public ParameterBuilder withAnnotation(AnnotationWrapper annotation) {
		annotations.add(annotation.getNode());
		withImports(annotation.getImports().stream());
		return this;
	}

	@Override
	public ParameterBuilder withImport(String canonicalClassName) {
		imports.add(canonicalClassName);
		return this;
	}

	public ParameterWrapper build() {
		return new ParameterWrapper() {
			@NotNull
			@Override
			public UnmodifiableSet<String> getImports() {
				return new UnmodifiableSet<>(imports);
			}

			@Override
			public Parameter getNode() {
				Type type = new ClassOrInterfaceType(typeName);
				type.setAnnotations(annotations);
				return new Parameter(type, new VariableDeclaratorId(name));
			}
		};
	}
}
