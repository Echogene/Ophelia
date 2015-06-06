package ophelia.util;

import ophelia.map.UnmodifiableEntry;
import ophelia.map.UnmodifiableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
public class MapUtils {

	@NotNull
	public static <K, V> String mapToString(@NotNull Map<K, V> map, @NotNull Function<V, String> valuePrinter) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		map.entrySet().stream()
				.forEach(
						(entry) ->
								sb.append("\t")
										.append(entry.getKey())
										.append(" → ")
										.append(valuePrinter.apply(entry.getValue()))
										.append("\n")
				);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Update a set-based map by adding the given value to the set represented by a key.
	 * @param map the map to update
	 * @param key the key to update
	 * @param value the set of value to add to the key's set
	 * @param <K> the key type
	 * @param <V> the value type
	 */
	public static <K, V> void updateSetBasedMap(@NotNull Map<K, Set<V>> map, @Nullable K key, @Nullable V value) {
		if (map.containsKey(key)) {
			map.get(key).add(value);
		} else {
			map.put(key, CollectionUtils.createSet(value));
		}
	}

	/**
	 * Update a list-based map by adding the given value to the list represented by a key.
	 * @param map the map to update
	 * @param key the key to update
	 * @param value the set of value to add to the key's set
	 * @param <K> the key type
	 * @param <V> the value type
	 */
	public static <K, V> void updateListBasedMap(@NotNull Map<K, List<V>> map, @Nullable K key, @Nullable V value) {
		if (map.containsKey(key)) {
			map.get(key).add(value);
		} else {
			ArrayList<V> newList = new ArrayList<>();
			newList.add(value);
			map.put(key, newList);
		}
	}

	/**
	 * Update a set-based map by adding (by union) the given set to the set represented by a key.
	 * @param map the map to update
	 * @param key the key to update
	 * @param values the set of values to unite with the key's set
	 * @param <K> the key type
	 * @param <V> the value type
	 */
	public static <K, V> void updateSetBasedMap(@NotNull Map<K, Set<V>> map, @Nullable K key, @NotNull Set<V> values) {
		if (map.containsKey(key)) {
			map.get(key).addAll(values);
		} else {
			map.put(key, new HashSet<>(values));
		}
	}

	@NotNull
	public static <K, V> Map<K, V> createMap(@Nullable K key, @Nullable V value) {
		Map<K, V> output = new HashMap<>();
		output.put(key, value);
		return output;
	}

	@NotNull
	public static <K, V> LinkedHashMap<K, V> createLinkedHashMap(@Nullable K key, @Nullable V value) {
		LinkedHashMap<K, V> output = new LinkedHashMap<>();
		output.put(key, value);
		return output;
	}

	@NotNull
	public static <K, V> Map<K, V> createMap(@NotNull List<K> keys, @NotNull List<V> values) {
		Map<K, V> output = new HashMap<>();
		int index = 0;
		for (K key : keys) {
			output.put(key, values.get(index));
			index++;
		}
		return output;
	}

	@NotNull
	public static <K, V> Map<K, V> createMap(@NotNull List<K> keys, @Nullable V value) {
		Map<K, V> output = new HashMap<>();
		for (K key : keys) {
			output.put(key, value);
		}
		return output;
	}

	/**
	 * Overlay onto a given map—whose values are sets—another such that:<ol>
	 *     <li>The first map will obtain all keys that it does not have already and values from the overlay</li>
	 *     <li>For each key contained in both maps, the first map's corresponding value set will be intersected with
	 *     value set of the overlay</li>
	 *     <li>The first map will retain all keys that the overlay does not contain</li>
	 * </ol>
	 *
	 * @param underlay the map to modify with the overlay
	 * @param overlay the map to overlay onto the underlay
	 * @param <K> the type of the maps' keys
	 * @param <V> the type of the maps' values
	 */
	public static <K, V> void overlay(
			@NotNull Map<K, Set<V>> underlay,
			@NotNull Map<K, Set<V>> overlay
	) {
		for (Map.Entry<K, Set<V>> entry : overlay.entrySet()) {
			K key = entry.getKey();
			Set<V> value = entry.getValue();
			overlay(underlay, key, value);
		}
	}

	public static <K, V> void overlay(
			@NotNull Map<K, Set<V>> underlay,
			@Nullable K key,
			@Nullable Set<V> value
	) {
		if (underlay.containsKey(key)) {
			if (value == null) {
				underlay.put(key, null);
			} else {
				underlay.get(key).retainAll(value);
			}
		} else {
			Set<V> newSet = value == null ? null : new HashSet<>(value);
			underlay.put(key, newSet);
		}
	}

	public static <K, V> void overlaySingleValues(@NotNull Map<K, Set<V>> underlay, @NotNull Map<K, V> overlay) {
		for (Map.Entry<K, V> entry : overlay.entrySet()) {
			K key = entry.getKey();
			V value = entry.getValue();
			Set<V> valueSet = Collections.singleton(value);
			if (underlay.containsKey(key)) {
				underlay.get(key).retainAll(valueSet);
			} else {
				underlay.put(key, valueSet);
			}
		}
	}

	/**
	 * Given a collection of maps from keys to sets, return the map whose keys are the intersection of all the maps'
	 * keys and whose values (as sets) are the intersection of all the maps' corresponding (by key) values.
	 * @param maps the maps we want to intersect
	 * @param <K> the type of the maps' keys
	 * @param <S> the type of the maps' values
	 * @return the intersection of all keys and values of the given maps.
	 */
	@NotNull
	public static <K, S extends Set<?>> Map<K, S> intersect(@NotNull Collection<Map<K, S>> maps) {
		Iterator<Map<K, S>> iterator = maps.iterator();
		Map<K, S> output = new HashMap<>(iterator.next());
		while (iterator.hasNext()) {
			Map<K, S> map = iterator.next();
			output.keySet().retainAll(map.keySet());
		}
		for (Map.Entry<K, S> entry : output.entrySet()) {
			for (Map<K, S> map : maps) {
				entry.getValue().retainAll(map.get(entry.getKey()));
			}
		}
		return output;
	}

	@NotNull
	public static <K, V> UnmodifiableEntry<K, V> $(@Nullable K key, @Nullable V value) {
		return new UnmodifiableEntry<>(key, value);
	}

	@NotNull
	@SafeVarargs
	public static <K, V> UnmodifiableMap<K, V> map(@NotNull UnmodifiableEntry<? extends K, ? extends V>... pairs) {

		Map<K, V> output = new HashMap<>();
		for (UnmodifiableEntry<? extends K, ? extends V> pair : pairs) {
			output.put(pair.getKey(), pair.getValue());
		}
		return new UnmodifiableMap<>(output);
	}

	@NotNull
	public static <K, V> String debugToString(@NotNull Map<K, V> map) {

		if (map.size() > 10) {
			return "size = " + map.size();
		}
		int count = 0;
		for (Map.Entry<K, V> entry : map.entrySet()) {
			count += StringUtils.safeToString(entry.getKey()).length();
			count += StringUtils.safeToString(entry.getValue()).length();
		}
		if (count > 100) {
			return "size = " + map.size();
		}
		return map.toString();
	}
}
