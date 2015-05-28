package ophelia.generator.relationship.onetomany;

import ophelia.generator.MainClassBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface OneToManyHierarchyBuilderNeedingChildBuilder {

	@NotNull MainOneToManyHierarchyBuilder withChildBuilder(@NotNull MainClassBuilder childBuilder);
}
