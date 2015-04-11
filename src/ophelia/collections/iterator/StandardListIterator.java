package ophelia.collections.iterator;

import ophelia.annotation.Wrapper;

import java.util.ListIterator;

/**
 * @author Steven Weston
 */
@Wrapper(ListIterator.class)
public class StandardListIterator<E> implements ModifiableListIterator<E>, ListIterator<E> {

	private final ListIterator<E> listIterator;

	public StandardListIterator(ListIterator<E> listIterator) {
		this.listIterator = listIterator;
	}

	@Override
	public void remove() {
		listIterator.remove();
	}

	@Override
	public void set(E e) {
		listIterator.set(e);
	}

	@Override
	public void add(E e) {
		listIterator.add(e);
	}

	@Override
	public boolean hasPrevious() {
		return listIterator.hasPrevious();
	}

	@Override
	public E previous() {
		return listIterator.previous();
	}

	@Override
	public int nextIndex() {
		return listIterator.nextIndex();
	}

	@Override
	public int previousIndex() {
		return listIterator.previousIndex();
	}

	@Override
	public boolean hasNext() {
		return listIterator.hasNext();
	}

	@Override
	public E next() {
		return listIterator.next();
	}
}
