package ophelia.graph;

import ophelia.collections.ModifiableCollection;
import ophelia.collections.pair.Pair;

public interface ModifiableBiGraph<N, E extends Pair<N>> extends BiGraph<N, E> {

	@Override
	ModifiableCollection<E> getEdges();

	@Override
	ModifiableCollection<N> getNodes();
}
