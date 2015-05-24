package ophelia.generator.type;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.WithImportBuilder;
import ophelia.generator.annotation.AnnotationWrapper;
import ophelia.util.ClassUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class TypeBuilder implements WithImportBuilder<TypeWrapper, TypeBuilder> {

	private final String typeName;
	private final Set<String> imports = new HashSet<>();
	private final List<AnnotationExpr> annotations = new ArrayList<>();

	@Nullable
	private List<Type> genericTypes = null;

	public TypeBuilder(String canonicalClassName) {
		this.typeName = ClassUtils.getSimpleName(canonicalClassName);
		withImport(canonicalClassName);
	}

	public TypeBuilder(Class<?> clazz) {
		this(clazz.getCanonicalName());
	}

	@NotNull
	public TypeBuilder withAnnotation(final AnnotationWrapper annotation) {
		annotations.add(annotation.getNode());
		withImports(annotation.getImports());
		return this;
	}

	@NotNull
	public TypeBuilder withGenericParameter(String type) {
		return withGenericParameter(new TypeBuilder(type).build());
	}

	@NotNull
	public TypeBuilder withGenericParameter(Class<?> type) {
		return withGenericParameter(type.getCanonicalName());
	}

	@NotNull
	public TypeBuilder withGenericParameter(TypeWrapper type) {
		if (genericTypes == null) {
			withDiamond();
		}
		genericTypes.add(type.getNode());
		withImports(type.getImports());
		return this;
	}

	@NotNull
	public TypeBuilder withDiamond() {
		genericTypes = new ArrayList<>();
		return this;
	}

	@NotNull
	@Override
	public TypeBuilder withImport(String canonicalClassName) {
		imports.add(canonicalClassName);
		return this;
	}

	@NotNull
	@Override
	public TypeBuilder noöp() {
		return this;
	}

	@NotNull
	@Override
	public TypeWrapper build() {
		return new TypeWrapper() {
			@NotNull
			@Override
			public UnmodifiableSet<String> getImports() {
				return new UnmodifiableSet<>(imports);
			}

			@Override
			public Type getNode() {
				ClassOrInterfaceType type = new ClassOrInterfaceType(typeName);
				type.setAnnotations(annotations);
				type.setTypeArgs(genericTypes);
				return type;
			}
		};
	}
}
