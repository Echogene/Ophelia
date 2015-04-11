package ophelia.collections.set;

import ophelia.collections.iterator.StandardIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class HashSet<E> implements ModifiableSet<E, StandardIterator<E>>, Set<E> {

	private final java.util.HashSet<E> set;

	public HashSet() {
		set = new java.util.HashSet<>();
	}

	public HashSet(int initialCapacity) {
		set = new java.util.HashSet<>(initialCapacity);
	}

	public HashSet(int initialCapacity, float loadFactor) {
		set = new java.util.HashSet<>(initialCapacity, loadFactor);
	}

	public HashSet(@NotNull Collection<? extends E> c) {
		set = new java.util.HashSet<>(c);
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

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

	@Override
	public StandardIterator<E> iterator() {
		return new StandardIterator<>(set.iterator());
	}

	@Override
	public boolean add(E e) {
		return set.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return set.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	@Override
	public void clear() {
		set.clear();
	}
}


