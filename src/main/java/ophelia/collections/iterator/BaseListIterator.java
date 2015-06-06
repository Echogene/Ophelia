package ophelia.collections.iterator;

import java.util.ListIterator;

/**
 * A trimmed-down version of {@link ListIterator} that contains the read-only methods.
 * @author Steven Weston
 */
public interface BaseListIterator<E> extends BaseIterator<E> {

	/**
	 * See {@link ListIterator#hasPrevious()}
	 */
	boolean hasPrevious();

	/**
	 * See {@link ListIterator#previous()}
	 */
	E previous();

	/**
	 * See {@link ListIterator#nextIndex()}
	 */
	int nextIndex();

	/**
	 * See {@link ListIterator#previousIndex()}
	 */
	int previousIndex();
}
