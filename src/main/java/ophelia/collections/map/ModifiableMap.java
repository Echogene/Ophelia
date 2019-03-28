package ophelia.collections.map;

import ophelia.collections.ModifiableCollection;
import ophelia.collections.set.ModifiableSet;

import java.util.Map;

public interface ModifiableMap<
        K,
        V,
        KS extends ModifiableSet<K>,
        VC extends ModifiableCollection<V>,
        E extends ModifiableMap.ModifiableMapEntry<K, V>,
        ES extends ModifiableSet<E>
> extends BaseMap<K, V, KS, VC, E, ES> {

    interface ModifiableMapEntry<K, V> extends BaseMapEntry<K, V> {
        /**
         * @see Map.Entry#setValue(Object)
         */
        V setValue(V value);
    }
}
