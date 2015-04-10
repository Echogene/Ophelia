package ophelia.collections.iterator;

import java.util.Iterator;

/**
 * A wrapper for {@link java.util.Iterator} that extends the split-up interfaces.
 * @author Steven Weston
 */
public class StandardIterator<E> implements ModifiableIterator<E>, Iterator<E> {

	private final Iterator<E> iterator;

	public StandardIterator(Iterator<E> iterator) {
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
