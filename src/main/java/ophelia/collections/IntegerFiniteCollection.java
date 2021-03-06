package ophelia.collections;

import ophelia.collections.iterator.BaseIterable;
import ophelia.collections.iterator.BaseIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * A finite collection whose size can fit inside an Integer.  It has copies of the readonly methods from
 * {@link Collection}.
 * @author Steven Weston
 */
public interface IntegerFiniteCollection<E, I extends BaseIterator<E>> extends BaseCollection<E>, BaseIterable<E, I> {

	/**
	 * See {@link Collection#size()}
	 */
	int size();

	/**
	 * See {@link Collection#toArray()}
	 */
	@NotNull
	Object[] toArray();

	/**
	 * See {@link Collection#toArray(Object[])}
	 */
	@NotNull
	<T> T[] toArray(T[] a);
}
