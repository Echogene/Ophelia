package ophelia.collections.list;

import ophelia.collections.ModifiableCollection;
import ophelia.collections.iterator.ModifiableIterator;
import ophelia.collections.iterator.ModifiableListIterator;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * A {@link BaseList} with the modification methods of {@link List} added.
 * @author Steven Weston
 */
public interface ModifiableList<E, I extends ModifiableIterator<E>, L extends ModifiableListIterator<E>>
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

	/**
	 * See {@link List#sort(Comparator)}
	 */
	void sort(Comparator<? super E> c);

	/**
	 * See {@link List#replaceAll(UnaryOperator)}
	 */
	void replaceAll(UnaryOperator<E> operator);
}
