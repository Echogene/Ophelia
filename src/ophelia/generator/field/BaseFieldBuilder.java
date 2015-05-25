package ophelia.generator.field;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.Type;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.generator.type.TypeWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static java.lang.reflect.Modifier.*;

class BaseFieldBuilder implements MainFieldBuilder {

	private final Type type;
	private final String fieldName;
	private final Set<String> imports = new HashSet<>();

	private Expression init;
	private int modifiers;

	public BaseFieldBuilder(@NotNull TypeWrapper type, @NotNull String fieldName) {
		this.type = type.getNode();
		this.fieldName = fieldName;

		withImports(type.getImports());

		modifiers = FINAL | PRIVATE;
	}

	@NotNull
	@Override
	public MainFieldBuilder withImport(@NotNull String canonicalClassName) {
		imports.add(canonicalClassName);
		return this;
	}

	@NotNull
	@Override
	public MainFieldBuilder withPublicity() {
		modifiers = (modifiers & ~PRIVATE & ~PROTECTED) | PUBLIC;
		return this;
	}

	@NotNull
	@Override
	public MainFieldBuilder withProtection() {
		modifiers = (modifiers & ~PRIVATE & ~PUBLIC) | PROTECTED;
		return this;
	}

	@NotNull
	@Override
	public MainFieldBuilder withNoPrivacyModifier() {
		modifiers = modifiers & ~PUBLIC & ~PRIVATE & ~PROTECTED;
		return this;
	}

	@NotNull
	@Override
	public MainFieldBuilder withStasis() {
		modifiers = modifiers | STATIC;
		return this;
	}

	@NotNull
	@Override
	public MainFieldBuilder withNoFinality() {
		modifiers = modifiers & ~FINAL;
		return this;
	}

	@NotNull
	@Override
	public MainFieldBuilder withInitialisation(String initialisation) throws ParseException {
		init = JavaParser.parseExpression(initialisation);
		return this;
	}

	@NotNull
	@Override
	public MainFieldBuilder noöp() {
		return this;
	}

	@NotNull
	@Override
	public FieldWrapper build() {
		return new FieldWrapper() {
			@NotNull
			@Override
			public UnmodifiableSet<String> getImports() {
				return new UnmodifiableSet<>(imports);
			}

			@Override
			public FieldDeclaration getNode() {
				VariableDeclaratorId id = new VariableDeclaratorId(fieldName);
				VariableDeclarator variableDeclarator = new VariableDeclarator(id, init);
				return new FieldDeclaration(modifiers, type, variableDeclarator);
			}
		};
	}
}
