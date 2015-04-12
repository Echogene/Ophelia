package ophelia.collections.iterator;

import java.util.ListIterator;

/**
 * @author Steven Weston
 */
public final class UnmodifiableListIterator<E> implements BaseListIterator<E> {

	private final ListIterator<E> listIterator;

	public UnmodifiableListIterator(ListIterator<E> listIterator) {
		this.listIterator = listIterator;
	}

	@Override
	public boolean hasPrevious() {
		return listIterator.hasPrevious();
	}

	@Override
	public int previousIndex() {
		return listIterator.previousIndex();
	}

	@Override
	public E previous() {
		return listIterator.previous();
	}

	@Override
	public boolean hasNext() {
		return listIterator.hasNext();
	}

	@Override
	public int nextIndex() {
		return listIterator.nextIndex();
	}

	@Override
	public E next() {
		return listIterator.next();
	}
}
