package ophelia.collections;

import com.fasterxml.jackson.annotation.JsonValue;
import ophelia.annotation.Wrapper;
import ophelia.collections.iterator.UnmodifiableIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * A wrapper for {@link Collection} that only provides access to the methods present in {@link IntegerFiniteCollection}.
 * @author Steven Weston
 */
@Wrapper(Collection.class)
public final class UnmodifiableIntegerFiniteCollection<E>
		implements IntegerFiniteCollection<E, UnmodifiableIterator<E>> {

	private final Collection<E> collection;

	public UnmodifiableIntegerFiniteCollection(Collection<E> collection) {
		this.collection = collection;
	}

	@JsonValue
	private Collection<E> collection() {
		return collection;
	}

	@Override
	public int size() {
		return collection.size();
	}

	@NotNull
	@Override
	public Object[] toArray() {
		return collection.toArray();
	}

	@NotNull
	@Override
	public <T> T[] toArray(T[] a) {
		return collection.toArray(a);
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

	@NotNull
	@Override
	public UnmodifiableIterator<E> iterator() {
		return new UnmodifiableIterator<>(collection.iterator());
	}

	@Override
	public void forEach(Consumer<? super E> consumer) {
		collection.forEach(consumer);
	}
}
