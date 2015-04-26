package ophelia.map;

import ophelia.annotation.Wrapper;

import java.util.Map;

/**
 * @author Steven Weston
 */
@Wrapper(Map.Entry.class)
public final class UnmodifiableEntry<K, V> implements BaseEntry<K,V> {

	private final Map.Entry<K, V> entry;

	public UnmodifiableEntry(Map.Entry<K, V> entry) {
		this.entry = entry;
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
