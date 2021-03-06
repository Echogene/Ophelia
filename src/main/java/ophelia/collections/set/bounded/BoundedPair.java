package ophelia.collections.set.bounded;

import com.fasterxml.jackson.annotation.JsonValue;
import ophelia.collections.iterator.StandardIterator;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.IntegerFiniteSet;
import ophelia.collections.set.ModifiableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A {@link ModifiableSet} that can contain at most two non-null elements.  It cannot contain null elements.  If a
 * third non-null element is attempted to be added, a {@link BoundedSetOverflowException} will be thrown.
 * @param <E> the elements of the set
 */
public class BoundedPair<E> implements ModifiableSet<E>, IntegerFiniteSet<E, StandardIterator<E>>, Set<E> {

	@Nullable
	private E first = null;
	@Nullable
	private E second = null;

	/**
	 * Construct an initially empty {@code BoundedPair}.
	 */
	public BoundedPair() {}

	public BoundedPair(@NotNull E first) {
		this.first = first;
	}

	public BoundedPair(@NotNull E first, @NotNull E second) {
		this(first);
		this.second = second;
	}

	public BoundedPair(@NotNull Collection<? extends E> collection) {
		addAll(collection);
	}

	@JsonValue
	private Set<E> set() {
		Set<E> output = new HashSet<>();
		if (first != null) {
			output.add(first);
		}
		if (second != null) {
			output.add(second);
		}
		return output;
	}

	@Override
	public int size() {
		if (second != null) {
			return 2;
		}
		if (first != null) {
			return 1;
		}
		return 0;
	}

	@NotNull
	@Override
	public Object[] toArray() {
		if (second != null) {
			return new Object[] {first, second};
		}
		if (first != null) {
			return new Object[] {first};
		}
		return new Object[0];
	}

	@NotNull
	@Override
	public <T> T[] toArray(T[] a) {
		return set().toArray(a);
	}

	@Override
	public boolean add(@NotNull E e) {
		if (second != null && !contains(e)) {
			throw new BoundedSetOverflowException();
		}
		//noinspection ConstantConditions
		if (e == null) {
			throw new NullPointerException();
		}
		if (first == null) {
			first = e;
			return true;
		}
		// Don't add it as the second element if it's the same as the first.
		if (!first.equals(e)) {
			second = e;
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		if (first == null) {
			// Nothing can be removed.
			return false;
		}
		if (first.equals(o)) {
			// Remove the first element by moving it to refer to the second.
			first = second;
			second = null;
			return true;
		}
		if (second == null) {
			// The thing we wanted to remove was not the only element in this set, so we can't do anything.
			return false;
		}
		if (second.equals(o)) {
			// Remove the second element.
			second = null;
			return true;
		}
		return false;
	}

	@Override
	public boolean addAll(@NotNull Collection<? extends E> c) {
		boolean modified = false;
		for (E e : c) {
			if (add(e)) {
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean removeAll(@NotNull Collection<?> c) {
		boolean modified = false;
		Iterator<?> it = iterator();
		while (it.hasNext()) {
			if (c.contains(it.next())) {
				it.remove();
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean retainAll(@NotNull Collection<?> c) {
		return removeIf(e -> !c.contains(e));
	}

	@Override
	public void clear() {
		first = null;
		second = null;
	}

	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		boolean removed = false;
		if (filter.test(second)) {
			removed = remove(second);
		}
		if (filter.test(first)) {
			removed |= remove(first);
		}
		return removed;
	}

	@Override
	public boolean contains(Object o) {
		return o != null && (o.equals(first) || o.equals(second));
	}

	@Override
	public boolean isEmpty() {
		return first == null;
	}

	@Override
	public boolean containsAll(@NotNull Collection<?> c) {
		return c.stream().allMatch(this::contains);
	}

	@NotNull
	@Override
	public StandardIterator<E> iterator() {
		return new StandardIterator<>(set().iterator());
	}

	@Override
	public void forEach(Consumer<? super E> consumer) {
		if (first != null) {
			consumer.accept(first);
		}
		if (second != null) {
			consumer.accept(second);
		}
	}

	@Override
	public Spliterator<E> spliterator() {
		return set().spliterator();
	}

	@Override
	public Stream<E> stream() {
		return set().stream();
	}

	@Override
	public Stream<E> parallelStream() {
		return set().parallelStream();
	}

	@Nullable
	public E getFirst() {
		return first;
	}

	@Nullable
	public E getSecond() {
		return second;
	}

	@Nullable
	public E getOther(@NotNull E e) {

		if (e.equals(first)) {
			return second;

		} else if (e.equals(second)) {
			return first;

		} else {
			return null;
		}
	}
}
