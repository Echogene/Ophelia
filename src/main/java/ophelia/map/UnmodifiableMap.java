package ophelia.map;

import ophelia.annotation.Wrapper;
import ophelia.collections.UnmodifiableIntegerFiniteCollection;
import ophelia.collections.iterator.UnmodifiableIterator;
import ophelia.collections.set.UnmodifiableSet;

import java.util.Map;
import java.util.function.BiConsumer;

import static ophelia.annotation.WrapperUtils.wrap;

/**
 * @author Steven Weston
 */
@Wrapper(Map.class)
public final class UnmodifiableMap<K, V> implements IntegerFiniteMap<
		K,
		UnmodifiableSet<K>,
		UnmodifiableIterator<K>,
		V,
		UnmodifiableIntegerFiniteCollection<V>,
		UnmodifiableIterator<V>,
		UnmodifiableEntry<K, V>,
		UnmodifiableSet<UnmodifiableEntry<K, V>>,
		UnmodifiableIterator<UnmodifiableEntry<K, V>>
> {

	private final Map<K, V> map;

	public UnmodifiableMap(Map<K, V> map) {
		this.map = map;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public UnmodifiableSet<K> keySet() {
		return new UnmodifiableSet<>(map.keySet());
	}

	@Override
	public UnmodifiableIntegerFiniteCollection<V> values() {
		return new UnmodifiableIntegerFiniteCollection<>(map.values());
	}

	@Override
	public UnmodifiableSet<UnmodifiableEntry<K, V>> entrySet() {
		return new UnmodifiableSet<>(wrap(UnmodifiableEntry::new, map.entrySet()));
	}

	@Override
	public V getOrDefault(Object key, V defaultValue) {
		return map.getOrDefault(key, defaultValue);
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		map.forEach(action);
	}
}
