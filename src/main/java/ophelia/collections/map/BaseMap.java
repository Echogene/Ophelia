package ophelia.collections.map;

import ophelia.collections.BaseCollection;
import ophelia.collections.iterator.BaseIterable;
import ophelia.collections.iterator.BaseIterator;
import ophelia.collections.set.BaseSet;
import ophelia.tuple.Pair;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public interface BaseMap<
        K,
        V,
        KS extends BaseSet<K>,
        VC extends BaseCollection<V>,
        E extends BaseMap.BaseMapEntry<K, V>,
        ES extends BaseSet<E>
> {

    /**
     * @see Map#isEmpty()
     */
    boolean isEmpty();

    /**
     * @see Map#containsKey(Object)
     */
    boolean containsKey(Object key);

    /**
     * @see Map#containsValue(Object)
     */
    boolean containsValue(Object value);

    /**
     * @see Map#get(Object)
     */
    V get(Object key);

    /**
     * @see Map#keySet()
     */
    KS keySet();

    /**
     * @see Map#values()
     */
    VC values();

    /**
     * @see Map#entrySet()
     */
    ES entrySet();

    interface BaseMapEntry<K, V> {
        /**
         * @see Map.Entry#getKey()
         */
        K getKey();

        /**
         * @see Map.Entry#getValue()
         */
        V getValue();

        boolean equals(Object o);

        int hashCode();
    }

    boolean equals(Object o);

    int hashCode();

    default V getOrDefault(Object key, V defaultValue) {
        V v;
        return (((v = get(key)) != null) || containsKey(key))
                ? v
                : defaultValue;
    }

    default void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        entrySet().stream().forEach(entry -> {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch (IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        });
    }
}
