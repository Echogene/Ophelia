package ophelia.collections;

import java.util.Collection;
import java.util.Spliterator;
import java.util.stream.Stream;

/**
 * Represents a potentially infinite collection of things.  It is a stripped-down version of {@link Collection}.  It
 * makes no reference to its size, iterators or any modification methods.
 * @author Steven Weston
 */
public interface BaseCollection<E> {

	/**
	 * See {@link Collection#contains(Object)}
	 */
	boolean contains(Object o);

	/**
	 * See {@link Collection#isEmpty()}
	 */
	boolean isEmpty();

	/**
	 * See {@link Collection#containsAll(Collection)}
	 */
	boolean containsAll(Collection<?> c);

	/**
	 * See {@link Collection#spliterator()}
	 */
	Spliterator<E> spliterator();

	/**
	 * See {@link Collection#stream()}
	 */
	Stream<E> stream();

	/**
	 * See {@link Collection#parallelStream()}
	 */
	Stream<E> parallelStream();
}
