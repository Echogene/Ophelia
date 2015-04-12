package ophelia.graph.edge;

import ophelia.collections.UnorderedPair;
import ophelia.graph.Node;

/**
 * A standard edge that connects two nodes.
 * @author Steven Weston
 */
public interface BiEdge<N extends Node, P extends UnorderedPair<N>> extends Edge<N, P> {
}
