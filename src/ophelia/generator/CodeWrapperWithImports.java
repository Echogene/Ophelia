package ophelia.generator;

import ophelia.collections.list.UnmodifiableList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface CodeWrapperWithImports extends CodeWrapper {

	@NotNull UnmodifiableList<String> getImports();
}
