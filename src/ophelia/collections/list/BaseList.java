package ophelia.collections.list;

import ophelia.collections.IntegerFiniteCollection;
import ophelia.collections.iterator.BaseIterator;
import ophelia.collections.iterator.BaseListIterator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A stripped-down version of {@link List} that has only readonly methods.
 * @author Steven Weston
 */
public interface BaseList<E, I extends BaseIterator<E>, L extends BaseListIterator<E>>
		extends IntegerFiniteCollection<E, I> {

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
	@NotNull
	L listIterator();

	/**
	 * See {@link List#listIterator(int)}
	 */
	@NotNull
	L listIterator(int index);

	/**
	 * See {@link List#subList(int, int)}
	 */
	@NotNull
	List<E> subList(int fromIndex, int toIndex);
}
