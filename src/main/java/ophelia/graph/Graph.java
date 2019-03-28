package ophelia.graph;

import ophelia.collections.BaseCollection;
import ophelia.collections.set.HashSet;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;


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

	@NotNull
	default BaseCollection<E> getEdges(@NotNull N node) {
		return getEdges().stream()
				.filter(e -> e.contains(node))
				.collect(toCollection(HashSet::new));
	}

	/**
	 * @return All of the nodes in the graph.
	 */
	@NotNull
	BaseCollection<N> getNodes();

	/**
	 * Return all nodes that share an edge with a given node.  Perhaps unexpectedly, this always includes the given
	 * node (because {@link #isAdjacentTo(Object, Object)} is reflexive).
	 */
	@NotNull
	default BaseCollection<N> getNeighbours(@NotNull N node) {
		return getEdges().stream()
				.filter(e -> e.contains(node))
				.flatMap(BaseCollection::stream)
				.collect(toCollection(HashSet::new));
	}

	/**
	 * Two nodes are adjacent when there is an edge that contains them both.  This is reflexive <i>i.e.</i> every node
	 * is adjacent to itself.
	 */
	default boolean isAdjacentTo(@NotNull N node1, @NotNull N node2) {
		return getEdges().stream()
				.anyMatch(e -> e.contains(node1) && e.contains(node2));
	}
}
