package ophelia.collections;

/**
 * Represents a potentially infinite collection of things.  It is a stripped-down version of
 * {@link java.util.Collection}.
 * @author Steven Weston
 */
public interface Collection<E> {

	/**
	 * See {@link java.util.Collection#contains(Object)}
	 */
	boolean contains(Object o);

	/**
	 * See {@link java.util.Collection#isEmpty()}
	 */
	boolean isEmpty();

	/**
	 * See {@link java.util.Collection#containsAll(java.util.Collection)}
	 */
	boolean containsAll(java.util.Collection<?> c);
}
