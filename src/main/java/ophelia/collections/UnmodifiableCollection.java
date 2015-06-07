package ophelia.collections;

import ophelia.annotation.Wrapper;

import java.util.Collection;
import java.util.Spliterator;
import java.util.stream.Stream;

/**
 * @author Steven Weston
 */
@Wrapper(Collection.class)
public final class UnmodifiableCollection<E> implements BaseCollection<E> {

	private final Collection<E> collection;

	public UnmodifiableCollection(Collection<E> collection) {
		this.collection = collection;
	}

	@Override
	public boolean contains(Object o) {
		return collection.contains(o);
	}

	@Override
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return collection.containsAll(c);
	}

	@Override
	public Spliterator<E> spliterator() {
		return collection.spliterator();
	}

	@Override
	public Stream<E> stream() {
		return collection.stream();
	}

	@Override
	public Stream<E> parallelStream() {
		return collection.parallelStream();
	}
}
