package ophelia.collections.pair;

import ophelia.collections.iterator.StandardIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Steven Weston
 */
abstract class AbstractPair<E> implements Pair<E> {

	final E first;
	final E second;

	AbstractPair(@NotNull E first, @NotNull E second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Spliterator<E> spliterator() {
		return getAsList().spliterator();
	}

	@Override
	public Stream<E> stream() {
		return getAsList().stream();
	}

	@Override
	public Stream<E> parallelStream() {
		return getAsList().parallelStream();
	}

	@Override
	public boolean contains(Object o) {
		return first.equals(o) || second.equals(o);
	}

	@NotNull
	@Override
	public StandardIterator<E> iterator() {
		return new StandardIterator<>(getAsList().iterator());
	}

	@Override
	public void forEach(Consumer<? super E> consumer) {

		consumer.accept(first);
		consumer.accept(second);
	}

	@NotNull
	List<E> getAsList() {
		return Arrays.asList(first, second);
	}

	@NotNull
	@Override
	public Object[] toArray() {
		return new Object[] {first, second};
	}

	@SuppressWarnings("unchecked")
	@NotNull
	@Override
	public <T> T[] toArray(@NotNull T[] a) {
		if (a.length > 1) {
			a[0] = (T) first;
			a[1] = (T) second;
			return a;
		} else {
			return (T[]) toArray();
		}
	}
}
