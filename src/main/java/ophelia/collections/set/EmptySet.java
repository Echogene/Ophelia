package ophelia.collections.set;

import ophelia.collections.iterator.BaseIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class EmptySet<E> implements BaseSet<E>, IntegerFiniteSet<E, EmptySet.EmptyIterator<E>> {

	private static final EmptySet EMPTY_SET = new EmptySet();

	public static <E> EmptySet<E> emptySet() {
		//noinspection unchecked
		return EMPTY_SET;
	}

	private EmptySet() {}

	@Override
	public int size() {
		return 0;
	}

	@NotNull
	@Override
	public Object[] toArray() {
		return new Object[0];
	}

	@NotNull
	@Override
	public <T> T[] toArray(T[] a) {
		return Collections.emptySet().toArray(a);
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return c.isEmpty();
	}

	@NotNull
	@Override
	public EmptyIterator<E> iterator() {
		return new EmptyIterator<>();
	}

	@Override
	public void forEach(Consumer<? super E> consumer) {
		// Do nothing.
	}

	@Override
	public Spliterator<E> spliterator() {
		return Collections.<E>emptySet().spliterator();
	}

	@Override
	public Stream<E> stream() {
		return Stream.empty();
	}

	@Override
	public Stream<E> parallelStream() {
		return Collections.<E>emptySet().parallelStream();
	}

	public static class EmptyIterator<E> implements BaseIterator<E> {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public E next() {
			return null;
		}

		@Override
		public void forEachRemaining(Consumer<? super E> consumer) {
			// Do nothing;
		}
	}
}
