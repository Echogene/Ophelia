package ophelia.graph.edge;

import ophelia.collections.BaseCollection;
import ophelia.graph.Node;


/**
 * An edge connects a collection of nodes (the ends) together in a graph.
 * @author Steven Weston
 */
public interface Edge<N extends Node, C extends BaseCollection<N>> {

	C getEnds();
}
