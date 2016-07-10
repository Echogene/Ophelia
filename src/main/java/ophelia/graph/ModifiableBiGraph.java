package ophelia.graph;

import ophelia.collections.ModifiableCollection;
import ophelia.collections.pair.Pair;
import org.jetbrains.annotations.NotNull;

public interface ModifiableBiGraph<N, E extends Pair<N>> extends BiGraph<N, E> {

	@NotNull
	@Override
	ModifiableCollection<E> getEdges();

	@NotNull
	@Override
	ModifiableCollection<N> getNodes();
}
