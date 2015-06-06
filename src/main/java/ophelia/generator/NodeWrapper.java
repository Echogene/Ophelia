package ophelia.generator;

import com.github.javaparser.ast.Node;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface NodeWrapper<N extends Node> extends CodeWrapper {

	@NotNull N getNode();
}
