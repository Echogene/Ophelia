package ophelia.collections.list;

import ophelia.collections.ModifiableCollection;
import ophelia.collections.iterator.BaseIterator;
import ophelia.collections.iterator.BaseListIterator;

import java.util.Collection;
import java.util.List;

/**
 * @author Steven Weston
 */
public interface ModifiableList<E, I extends BaseIterator<E>, L extends BaseListIterator<E>>
		extends BaseList<E, I, L>, ModifiableCollection<E> {

	/**
	 * See {@link List#addAll(int, Collection)}
	 */
	boolean addAll(int index, Collection<? extends E> c);

	/**
	 * See {@link List#set(int, Object)}
	 */
	E set(int index, E element);

	/**
	 * See {@link List#add(int, Object)}
	 */
	void add(int index, E element);

	/**
	 * See {@link List#remove(int)}
	 */
	E remove(int index);
}
