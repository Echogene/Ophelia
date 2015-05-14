package ophelia.generator;

import com.github.javaparser.ast.Node;

/**
 * @author Steven Weston
 */
public interface NodeWrapper<N extends Node> extends CodeWrapper {

	N getNode();
}
