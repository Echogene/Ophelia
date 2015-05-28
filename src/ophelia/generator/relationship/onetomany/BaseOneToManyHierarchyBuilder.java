package ophelia.generator.relationship.onetomany;

import com.github.javaparser.ParseException;
import ophelia.generator.MainClassBuilder;
import ophelia.generator.field.FieldBuilder;
import ophelia.generator.method.MethodBuilder;
import ophelia.generator.method.parameter.ParameterBuilder;
import ophelia.generator.type.TypeBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.text.MessageFormat.format;
import static ophelia.util.StringUtils.uppercaseFirst;

/**
 * @author Steven Weston
 */
class BaseOneToManyHierarchyBuilder implements MainOneToManyHierarchyBuilder {

	private final MainClassBuilder parentBuilder;
	private final MainClassBuilder childBuilder;
	@NotNull
	private String parentName;

	@NotNull
	private String childName;

	@NotNull
	private String childrenName;

	public BaseOneToManyHierarchyBuilder(
			@NotNull final MainClassBuilder parentBuilder,
			@NotNull final MainClassBuilder childBuilder
	) {
		this.parentBuilder = parentBuilder;
		this.childBuilder = childBuilder;

		this.parentName = "parent";
		this.childName = "child";
		this.childrenName = "children";
	}

	@NotNull
	@Override
	public MainOneToManyHierarchyBuilder withParentName(@NotNull final String parentName) {
		this.parentName = parentName;
		return this;
	}

	@NotNull
	@Override
	public MainOneToManyHierarchyBuilder withChildrenName(
			@NotNull String singularChildName,
			@NotNull String pluralChildName
	) {
		this.childName = singularChildName;
		this.childrenName = pluralChildName;
		return this;
	}

	@NotNull
	@Override
	public MainOneToManyHierarchyBuilder noöp() {
		return this;
	}

	@Override
	public void build() {

		try {
			updateParent();
			updateChild();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private void updateChild() throws ParseException {

		String parentTypeName = parentBuilder.getCanonicalClassName();

		childBuilder.withField(
						new FieldBuilder(parentName)
								.withType(parentTypeName)
								.withAnnotation(Nullable.class)
								.withNoFinality()
								.build()
				)
				.withMethod(
						new MethodBuilder("get" + uppercaseFirst(parentName))
								.withReturnType(parentTypeName)
								.withAnnotation(Nullable.class)
								.withImplementation("return " + parentName + ";")
								.build()
				)
				.withMethod(
						new MethodBuilder(getParentSetterName())
								.withVoidType()
								.withParameter(
										new ParameterBuilder(parentName)
												.withType(
														new TypeBuilder(parentTypeName)
																.withAnnotation(Nullable.class)
																.build()
												)
												.build()
								)
								.withImplementation(
										format(
												"if ({0} == this.{0}) '{'return;'}'\n"
														+ "if (this.{0} != null) '{'this.{0}.{1}(this);'}'\n"
														+ "this.{0} = {0};\n"
														+ "if (this.{0} != null) '{'this.{0}.{2}(this);'}'",
												parentName,
												getRemoveChildName(),
												getAddChildName()
										)
								)
								.build()
				);
	}

	@NotNull
	private String getParentSetterName() {
		return "set" + uppercaseFirst(parentName);
	}

	private void updateParent() throws ParseException {

		final String childTypeName = childBuilder.getCanonicalClassName();

		parentBuilder.withSetMember(childrenName, childTypeName)
				.withMethod(
						new MethodBuilder(getAddChildName())
								.withVoidType()
								.withParameter(
										new ParameterBuilder(childName)
												.withType(
														new TypeBuilder(childTypeName)
																.withAnnotation(NotNull.class)
																.build()
												)
												.build()
								)
								.withImplementation(
										format(
												"if ({0}.contains({1})) '{'return;'}'\n"
														+ "{0}.add({1});\n"
														+ "{1}.{2}(this);",
												childrenName,
												childName,
												getParentSetterName()
										)
								)
								.build()
				)
				.withMethod(
						new MethodBuilder(getRemoveChildName())
								.withVoidType()
								.withParameter(
										new ParameterBuilder(childName)
												.withType(
														new TypeBuilder(childTypeName)
																.withAnnotation(NotNull.class)
																.build()
												)
												.build()
								)
								.withImplementation(
										format(
												"if (!{0}.contains({1})) '{'return;'}'\n"
														+ "{0}.remove({1});\n"
														+ "{1}.{2}(null);",
												childrenName,
												childName,
												getParentSetterName()
										)
								)
								.build()
				)
				.withMethod(
						new MethodBuilder("removeAll" + uppercaseFirst(childrenName))
								.withVoidType()
								.withImplementation(
										format(
												"if ({0}.isEmpty()) '{'return;'}'\n"
														+ "new HashSet<>({0}).forEach({1} -> {1}.{2}(null));",
												childrenName,
												childName,
												getParentSetterName()
										)
								)
								.build()
				);
	}

	@NotNull
	private String getRemoveChildName() {
		return "remove" + uppercaseFirst(childName);
	}

	@NotNull
	private String getAddChildName() {
		return "add" + uppercaseFirst(childName);
	}
}
