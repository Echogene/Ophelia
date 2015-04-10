package ophelia.collections;

/**
 * A finite collection whose size can fit inside an Integer.  It has copies of the readonly methods from
 * {@link java.util.Collection}.
 * @author Steven Weston
 */
public interface IntegerFiniteCollection<E> extends Collection<E>, Iterable<E> {

	/**
	 * See {@link java.util.Collection#size()}
	 */
	int size();

	/**
	 * See {@link java.util.Collection#toArray()}
	 */
	Object[] toArray();

	/**
	 * See {@link java.util.Collection#toArray(Object[])}
	 */
	<T> T[] toArray(T[] a);
}
