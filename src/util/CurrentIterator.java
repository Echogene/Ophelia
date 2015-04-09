package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator that stores the current element of the iteration and can access it multiple times.
 * @author Steven Weston
 */
public class CurrentIterator<T> implements Iterator<T> {

	private final Iterator<T> iterator;
	private T current;
	private boolean gonePastEnd;

	public CurrentIterator(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public T next() {
		try {
			current = iterator.next();
		} catch (NoSuchElementException e) {
			gonePastEnd = true;
			throw e;
		}
		return current;
	}

	/**
	 * @throws NoSuchElementException if next() has thrown a NoSuchElementException
	 */
	public T current() {
		if (gonePastEnd) {
			throw new NoSuchElementException();
		} else {
			return current;
		}
	}

	@Override
	public String toString() {
		return "Current: " + current;
	}
}
