package ophelia.map;

import java.util.Map.Entry;

/**
* A stripped-down version of {@link Entry}.
 * @author Steven Weston
 */
public interface BaseEntry<K, V> {

	/**
	 * See {@link Entry#getKey()}
	 */
	K getKey();

	/**
	 * See {@link Entry#getValue()}
	 */
	V getValue();
}
