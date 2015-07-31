package ophelia.map;

import ophelia.annotation.Wrapper;

import java.util.Map;

/**
 * An {@link UnmodifiableMap} needs an UnmodifiableEntry.
 * @author Steven Weston
 */
@Wrapper(Map.Entry.class)
public final class UnmodifiableEntry<K, V> implements BaseEntry<K, V> {

	private final Map.Entry<K, V> entry;

	public UnmodifiableEntry(Map.Entry<K, V> entry) {
		this.entry = entry;
	}

	public UnmodifiableEntry(K key, V value) {

		entry = new Map.Entry<K, V>() {
			@Override
			public K getKey() {
				return key;
			}

			@Override
			public V getValue() {
				return value;
			}

			@Override
			public V setValue(V value) {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public K getKey() {
		return entry.getKey();
	}

	@Override
	public V getValue() {
		return entry.getValue();
	}
}
