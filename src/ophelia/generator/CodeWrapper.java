package ophelia.generator;

import ophelia.collections.list.UnmodifiableList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface CodeWrapper {

	@NotNull UnmodifiableList<String> getImports();
}
