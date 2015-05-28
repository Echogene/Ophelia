package ophelia.generator.relationship.onetomany;

import ophelia.generator.MainClassBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public class OneToManyHierarchyBuilder implements OneToManyHierarchyBuilderNeedingChildBuilder {

	private final MainClassBuilder parentBuilder;

	public OneToManyHierarchyBuilder(@NotNull MainClassBuilder parentBuilder) {
		this.parentBuilder = parentBuilder;
	}

	@NotNull
	@Override
	public MainOneToManyHierarchyBuilder withChildBuilder(@NotNull final MainClassBuilder childBuilder) {

		return new BaseOneToManyHierarchyBuilder(parentBuilder, childBuilder);
	}
}
