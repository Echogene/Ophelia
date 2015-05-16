package ophelia.generator;

import com.github.javaparser.ast.Node;
import ophelia.collections.list.UnmodifiableList;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface CodeWrapperWithImports<N extends Node> extends NodeWrapper<N> {

	@NotNull UnmodifiableList<String> getImports();
}
