package ophelia.graph.edge;

import ophelia.graph.Node;

import java.util.Collection;

/**
 * An edge connects a collection of nodes (the ends) together in a graph.
 * @author Steven Weston
 */
public interface Edge<N extends Node, C extends Collection<N>> {

	C getEnds();
}
