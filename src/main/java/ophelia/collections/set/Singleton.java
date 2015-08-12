package ophelia.collections.set;

import com.fasterxml.jackson.annotation.JsonValue;
import ophelia.collections.iterator.UnmodifiableIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.Collections.singleton;

public final class Singleton<E> implements BaseSet<E, UnmodifiableIterator<E>> {

	private final E element;
	private final Set<E> set;

	public Singleton(@NotNull E element) {
		this.element = element;
		set = singleton(element);
	}

	@JsonValue
	private Set<E> set() {
		return set;
	}

	@Override
	public int size() {
		return 1;
	}

	@NotNull
	@Override
	public Object[] toArray() {
		return new Object[] {element};
	}

	@NotNull
	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	@Override
	public boolean contains(Object o) {
		return element.equals(o);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return c.stream().allMatch(this::contains);
	}

	@NotNull
	@Override
	public UnmodifiableIterator<E> iterator() {
		return new UnmodifiableIterator<>(set.iterator());
	}

	@Override
	public void forEach(Consumer<? super E> consumer) {
		consumer.accept(element);
	}

	@Override
	public Spliterator<E> spliterator() {
		return set.spliterator();
	}

	@Override
	public Stream<E> stream() {
		return set.stream();
	}

	@Override
	public Stream<E> parallelStream() {
		return set.parallelStream();
	}
}
