package ophelia.collections.iterator;

import ophelia.annotation.Wrapper;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
@Wrapper(Iterator.class)
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

	@Override
	public void forEachRemaining(Consumer<? super E> consumer) {
		iterator.forEachRemaining(consumer);
	}
}
