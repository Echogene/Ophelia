package ophelia.graph;

import ophelia.collections.pair.OrderedPair;

/**
 * A standard directed graph where each edge is an ordered pair of nodes.
 * @author Steven Weston
 */
public interface DiGraph<N, E extends OrderedPair<N>> extends BiGraph<N, E> {

}
