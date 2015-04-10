package ophelia.collections.list;

import ophelia.collections.IntegerFiniteCollection;
import ophelia.collections.iterator.BaseIterator;
import ophelia.collections.iterator.BaseListIterator;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface BaseList<E, I extends BaseIterator<E>, L extends BaseListIterator<E>> extends IntegerFiniteCollection<E, I> {

	/**
	 * See {@link List#get(int)}
	 */
	E get(int index);

	/**
	 * See {@link List#indexOf(Object)}
	 */
	int indexOf(Object o);

	/**
	 * See {@link List#lastIndexOf(Object)}
	 */
	int lastIndexOf(Object o);

	/**
	 * See {@link List#listIterator()}
	 */
	L listIterator();

	/**
	 * See {@link List#listIterator(int)}
	 */
	L listIterator(int index);

	/**
	 * See {@link List#subList(int, int)}
	 */
	List<E> subList(int fromIndex, int toIndex);
}
