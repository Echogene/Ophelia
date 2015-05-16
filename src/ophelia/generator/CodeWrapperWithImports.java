package ophelia.generator;

import com.github.javaparser.ast.Node;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface CodeWrapperWithImports<N extends Node> extends NodeWrapper<N> {

	@NotNull UnmodifiableSet<String> getImports();
}
