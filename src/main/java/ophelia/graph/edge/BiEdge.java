package ophelia.graph.edge;

import ophelia.collections.UnorderedPair;

/**
 * A standard edge that connects two nodes.
 * @author Steven Weston
 */
public interface BiEdge<N, P extends UnorderedPair<N>> extends Edge<N, P> {
}
