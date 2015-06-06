package ophelia.map;

import ophelia.collections.BaseCollection;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Represents a potentially infinite map of things.  It is a stripped-down version of {@link Map}.
 * @author Steven Weston
 */
public interface BaseMap<
		K,
		KC extends BaseCollection<K>,
		V,
		VC extends BaseCollection<V>,
		E extends BaseEntry<K, V>,
		EC extends BaseCollection<E>
> {

	/**
	 * See {@link Map#isEmpty()}
	 */
	boolean isEmpty();

	/**
	 * See {@link Map#containsKey(Object)}
	 */
	boolean containsKey(Object key);

	/**
	 * See {@link Map#containsValue(Object)}
	 */
	boolean containsValue(Object value);

	/**
	 * See {@link Map#get(Object)}
	 */
	V get(Object key);

	/**
	 * See {@link Map#keySet()}
	 */
	KC keySet();

	/**
	 * See {@link Map#values()}
	 */
	VC values();

	/**
	 * See {@link Map#entrySet()}
	 */
	EC entrySet();

	/**
	 * See {@link Map#getOrDefault(Object, Object)}
	 */
	V getOrDefault(Object key, V defaultValue);

	/**
	 * See {@link Map#forEach(BiConsumer)}
	 */
	void forEach(BiConsumer<? super K, ? super V> action);
}
