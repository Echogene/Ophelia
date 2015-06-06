package ophelia.collections;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author Steven Weston
 */
public interface ModifiableCollection<E> extends BaseCollection<E> {

	/**
	 * See {@link Collection#add(Object)}
	 */
	boolean add(E e);

	/**
	 * See {@link Collection#remove(Object)}
	 */
	boolean remove(Object o);

	/**
	 * See {@link Collection#addAll(Collection)}
	 */
	boolean addAll(Collection<? extends E> c);

	/**
	 * See {@link Collection#removeAll(Collection)}
	 */
	boolean removeAll(Collection<?> c);

	/**
	 * See {@link Collection#retainAll(Collection)}
	 */
	boolean retainAll(Collection<?> c);

	/**
	 * See {@link Collection#clear()}
	 */
	void clear();

	/**
	 * See {@link Collection#removeIf(Predicate)}
	 */
	boolean removeIf(Predicate<? super E> filter);
}
