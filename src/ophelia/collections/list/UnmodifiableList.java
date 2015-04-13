package ophelia.collections.list;

import ophelia.collections.iterator.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * @author Steven Weston
 */
public final class UnmodifiableList<E> implements BaseList<E, UnmodifiableIterator<E>, UnmodifiableListIterator<E>> {

	private final List<E> list;

	public UnmodifiableList(List<E> list) {
		this.list = list;
	}

	@Override
	public E get(int index) {
		return list.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@NotNull
	@Override
	public UnmodifiableListIterator<E> listIterator() {
		return new UnmodifiableListIterator<>(list.listIterator());
	}

	@NotNull
	@Override
	public UnmodifiableListIterator<E> listIterator(int index) {
		return new UnmodifiableListIterator<>(list.listIterator(index));
	}

	@NotNull
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public int size() {
		return list.size();
	}

	@NotNull
	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@NotNull
	@Override
	public <T> T[] toArray(@NotNull T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean containsAll(@NotNull Collection<?> c) {
		return list.containsAll(c);
	}

	@NotNull
	@Override
	public UnmodifiableIterator<E> iterator() {
		return new UnmodifiableIterator<>(list.iterator());
	}
}