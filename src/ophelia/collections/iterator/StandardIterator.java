package ophelia.collections.iterator;

import ophelia.annotation.Wrapper;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * A wrapper for {@link java.util.Iterator} that extends the split-up interfaces.
 * @author Steven Weston
 */
@Wrapper(Iterator.class)
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

	@Override
	public void forEachRemaining(Consumer<? super E> consumer) {
		iterator.forEachRemaining(consumer);
	}
}
