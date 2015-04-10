package ophelia.collections.iterator;

import java.util.ListIterator;

/**
 * @author Steven Weston
 */
public interface ModifiableListIterator<E> extends BaseListIterator<E> {

	/**
	 * See {@link ListIterator#remove()}
	 */
	void remove();

	/**
	 * See {@link ListIterator#set(Object)}
	 */
	void set(E e);

	/**
	 * See {@link ListIterator#add(Object)}
	 */
	void add(E e);
}
