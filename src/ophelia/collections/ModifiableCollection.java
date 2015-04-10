package ophelia.collections;

/**
 * @author Steven Weston
 */
public interface ModifiableCollection<E> extends Collection<E> {

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
	boolean addAll(java.util.Collection<? extends E> c);

	/**
	 * See {@link java.util.Collection#removeAll(java.util.Collection)}
	 */
	boolean removeAll(java.util.Collection<?> c);

	/**
	 * See {@link java.util.Collection#retainAll(java.util.Collection)}
	 */
	boolean retainAll(java.util.Collection<?> c);

	/**
	 * See {@link java.util.Collection#clear()}
	 */
	void clear();
}
