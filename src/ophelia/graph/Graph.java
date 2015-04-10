package ophelia.graph;

import ophelia.collections.Collection;
import ophelia.graph.edge.Edge;


/**
 * A graph is a collection of nodes linked by a collection of edges.
 * @author Steven Weston
 */
public interface Graph<N extends Node, E extends Edge<N, C>, C extends Collection<N>> {

	/**
	 * @return All the edges in the graph.
	 */
	Collection<E> getAllEdges();

	/**
	 * @return All of the nodes in the graph.
	 */
	Collection<N> getAllNodes();

	/**
	 * @param node The node for which we want to find all edges of which it is an end.
	 * @return The collection of edges that involve the given node as an end.
	 */
	Collection<E> getEdges(N node);
}
