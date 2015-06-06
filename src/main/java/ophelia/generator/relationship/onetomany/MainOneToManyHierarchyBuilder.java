package ophelia.generator.relationship.onetomany;

import ophelia.builder.VoidBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface MainOneToManyHierarchyBuilder extends VoidBuilder<MainOneToManyHierarchyBuilder> {

	@NotNull MainOneToManyHierarchyBuilder withParentName(@NotNull String parentName);

	@NotNull MainOneToManyHierarchyBuilder withChildrenName(
			@NotNull String singularChildName,
			@NotNull String pluralChildName
	);
}
