package ophelia.collections.iterator;

/**
 * @author Steven Weston
 */
public interface BaseIterator<E> {

	/**
	 * See {@link java.util.Iterator#hasNext()}
	 */
	boolean hasNext();

	/**
	 * See {@link java.util.Iterator#next()}
	 */
	E next();
}
