package ophelia.collections;

import ophelia.collections.iterator.BaseIterator;
import ophelia.collections.iterator.Iterable;

/**
 * A finite collection whose size can fit inside an Integer.  It has copies of the readonly methods from
 * {@link java.util.Collection}.
 * @author Steven Weston
 */
public interface IntegerFiniteCollection<E, I extends BaseIterator<E>> extends BaseCollection<E>, Iterable<E, I> {

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
