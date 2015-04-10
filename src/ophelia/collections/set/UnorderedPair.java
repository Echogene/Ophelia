package ophelia.collections.set;

import ophelia.collections.IntegerFiniteCollection;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Steven Weston
 */
public class UnorderedPair<E> implements IntegerFiniteCollection<E> {

	private final E first;
	private final E second;

	public UnorderedPair(@NotNull E first, @NotNull E second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return c.stream().allMatch(UnorderedPair.this::contains);
	}

	@Override
	public boolean contains(Object o) {
		return first.equals(o) || second.equals(o);
	}

	@NotNull
	@Override
	public Iterator<E> iterator() {
		return Arrays.asList(first, second).iterator();
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
