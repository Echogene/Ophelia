package ophelia.graph;

import ophelia.collections.pair.Pair;

/**
 * A standard graph where each edge connects two nodes.
 * @author Steven Weston
 */
public interface BiGraph<N, E extends Pair<N>> extends Graph<N, E> {

}
