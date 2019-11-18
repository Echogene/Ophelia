package ophelia.map;

import ophelia.collections.IntegerFiniteCollection;
import ophelia.collections.iterator.BaseIterator;
import ophelia.collections.set.IntegerFiniteSet;

import java.util.Map;

/**
 * @author Steven Weston
 */
public interface IntegerFiniteMap<
		K,
		KS extends IntegerFiniteSet<K, KI>,
		KI extends BaseIterator<K>,
		V,
		VS extends IntegerFiniteCollection<V, VI>,
		VI extends BaseIterator<V>,
		E extends BaseEntry<K, V>,
		ES extends IntegerFiniteSet<E, EI>,
		EI extends BaseIterator<E>
>
		extends BaseMap<K, KS, V, VS, E, ES> {

	/**
	 * See {@link Map#size()}
	 */
	int size();
}
