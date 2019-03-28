package ophelia.collections.map;

import ophelia.collections.IntegerFiniteCollection;
import ophelia.collections.iterator.BaseIterator;
import ophelia.collections.set.IntegerFiniteSet;

import java.util.Map;

public interface IntegerFiniteMap<
        K,
        V,
        KI extends BaseIterator<K>,
        KS extends IntegerFiniteSet<K, KI>,
        VI extends BaseIterator<V>,
        VC extends IntegerFiniteCollection<V, VI>,
        E extends BaseMap.BaseMapEntry<K, V>,
        EI extends BaseIterator<E>,
        ES extends IntegerFiniteSet<E, EI>
> extends BaseMap<K, V, KS, VC, E, ES> {

    /**
     * @see Map#size()
     */
    int size();
}
