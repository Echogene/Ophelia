package ophelia.collections.iterator;

import java.util.ListIterator;

/**
 * A list iterator that can remove, set the current element and add an element while it is being used.
 * @author Steven Weston
 */
public interface ModifiableListIterator<E> extends ModifiableIterator<E>, BaseListIterator<E> {

	/**
	 * See {@link ListIterator#set(Object)}
	 */
	void set(E e);

	/**
	 * See {@link ListIterator#add(Object)}
	 */
	void add(E e);
}
