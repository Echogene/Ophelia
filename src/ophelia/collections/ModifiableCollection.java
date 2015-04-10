package ophelia.collections;

import java.util.Collection;

/**
 * @author Steven Weston
 */
public interface ModifiableCollection<E> extends BaseCollection<E> {

	/**
	 * See {@link java.util.Collection#add(Object)}
	 */
	boolean add(E e);

	/**
	 * See {@link java.util.Collection#remove(Object)}
	 */
	boolean remove(Object o);

	/**
	 * See {@link java.util.Collection#addAll(java.util.Collection)}
	 */
	boolean addAll(Collection<? extends E> c);

	/**
	 * See {@link java.util.Collection#removeAll(java.util.Collection)}
	 */
	boolean removeAll(Collection<?> c);

	/**
	 * See {@link java.util.Collection#retainAll(java.util.Collection)}
	 */
	boolean retainAll(Collection<?> c);

	/**
	 * See {@link java.util.Collection#clear()}
	 */
	void clear();
}
