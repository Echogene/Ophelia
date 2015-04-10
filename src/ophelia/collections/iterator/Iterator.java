package ophelia.collections.iterator;

/**
 * A wrapper for {@link java.util.Iterator} that extends the split-up interfaces.
 * @author Steven Weston
 */
public class Iterator<E> implements BaseIterator<E>, RemovableIterator<E>, java.util.Iterator<E> {

	private final java.util.Iterator<E> iterator;

	public Iterator(java.util.Iterator<E> iterator) {
		this.iterator = iterator;
	}

	@Override
	public void remove() {
		iterator.remove();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public E next() {
		return iterator.next();
	}
}
