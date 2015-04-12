package ophelia.collections.iterator;

import java.util.Iterator;

/**
 * @author Steven Weston
 */
public final class UnmodifiableIterator<E> implements BaseIterator<E> {

	private final Iterator<E> iterator;

	public UnmodifiableIterator(Iterator<E> iterator) {
		this.iterator = iterator;
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
