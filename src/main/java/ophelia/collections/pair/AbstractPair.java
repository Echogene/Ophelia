package ophelia.collections.pair;

import com.fasterxml.jackson.annotation.JsonValue;
import ophelia.collections.iterator.StandardIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * An abstract implementation of {@link Pair} that is extended to make {@link OrderedPair}s and {@link UnorderedPair}s.
 * @param <E> the type of the elements in the pair
 * @author Steven Weston
 */
abstract class AbstractPair<E> implements Pair<E> {

	protected final E first;
	protected final E second;

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

	@JsonValue
	@NotNull
	private List<E> getAsList() {
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
