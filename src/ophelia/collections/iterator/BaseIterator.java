package ophelia.collections.iterator;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * A trimmed-down version of {@link Iterator} that contains the read-only methods.
 * @author Steven Weston
 */
public interface BaseIterator<E> {

	/**
	 * See {@link Iterator#hasNext()}
	 */
	boolean hasNext();

	/**
	 * See {@link Iterator#next()}
	 */
	E next();

	/**
	 * See {@link Iterator#forEachRemaining(Consumer)}
	 */
	void forEachRemaining(Consumer<? super E> consumer);
}
