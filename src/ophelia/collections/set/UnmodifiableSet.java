package ophelia.collections.set;

import ophelia.collections.iterator.UnmodifiableIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

/**
 * @author Steven Weston
 */
public final class UnmodifiableSet<E> implements BaseSet<E, UnmodifiableIterator<E>> {

	private final Set<E> set;

	public UnmodifiableSet(@NotNull Set<E> set) {
		this.set = set;
	}

	@Override
	public int size() {
		return set.size();
	}

	@NotNull
	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@NotNull
	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@NotNull
	@Override
	public UnmodifiableIterator<E> iterator() {
		return new UnmodifiableIterator<>(set.iterator());
	}
}
