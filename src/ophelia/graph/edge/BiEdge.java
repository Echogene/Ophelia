package ophelia.graph.edge;

import ophelia.collections.set.UnorderedPair;
import ophelia.graph.Node;

/**
 * A standard edge that connects two nodes.
 * @author Steven Weston
 */
public interface BiEdge<N extends Node> extends Edge<N, UnorderedPair<N>> {
}
