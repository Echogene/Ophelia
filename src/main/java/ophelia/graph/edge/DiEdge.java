package ophelia.graph.edge;

import ophelia.collections.pair.OrderedPair;

/**
 * A standard directed edge that points from one edge to another
 * @author Steven Weston
 */
public interface DiEdge<N, P extends OrderedPair<N>> extends BiEdge<N, P> {

}
