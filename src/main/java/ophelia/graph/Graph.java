package ophelia.graph;

import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;


/**
 * A graph is a collection of nodes linked by a collection of edges.
 * @author Steven Weston
 */
public interface Graph<N, E extends BaseCollection<N>> {

	/**
	 * @return All the edges in the graph.
	 */
	@NotNull
	BaseCollection<E> getEdges();

	/**
	 * @return All of the nodes in the graph.
	 */
	@NotNull
	BaseCollection<N> getNodes();

	/**
	 * Two nodes are adjacent when there is an edge that contains them both.
	 */
	default boolean isAdjacentTo(@NotNull N node1, @NotNull N node2) {
		return getEdges().stream()
				.anyMatch(e -> e.contains(node1) && e.contains(node2));
	}
}
