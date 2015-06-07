package ophelia.graph.edge;

import ophelia.collections.BaseCollection;


/**
 * An edge connects a collection of nodes (the ends) together in a graph.
 * @author Steven Weston
 */
public interface Edge<N, C extends BaseCollection<N>> {

	C getEnds();
}
