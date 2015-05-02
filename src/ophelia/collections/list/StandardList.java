package ophelia.collections.list;

import ophelia.annotation.Wrapper;
import ophelia.collections.iterator.StandardIterator;
import ophelia.collections.iterator.StandardListIterator;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A wrapper for {@link List} that extends the split-up interfaces.
 * @author Steven Weston
 */
@Wrapper(List.class)
public class StandardList<E>
		implements ModifiableList<E, StandardIterator<E>, StandardListIterator<E>>,
				List<E>  {

	private final List<E> list;

	public StandardList(List<E> list) {
		this.list = list;
	}

	@Override
	public boolean addAll(int index, @NotNull Collection<? extends E> c) {
		return list.addAll(index, c);
	}

	@Override
	public E set(int index, E element) {
		return list.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		list.add(index, element);
	}

	@Override
	public E remove(int index) {
		return list.remove(index);
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
	public StandardListIterator<E> listIterator() {
		return new StandardListIterator<>(list.listIterator());
	}

	@NotNull
	@Override
	public StandardListIterator<E> listIterator(int index) {
		return new StandardListIterator<>(list.listIterator(index));
	}

	@NotNull
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public void sort(Comparator<? super E> c) {
		list.sort(c);
	}

	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		list.replaceAll(operator);
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

	@Override
	public Spliterator<E> spliterator() {
		return list.spliterator();
	}

	@Override
	public Stream<E> stream() {
		return list.stream();
	}

	@Override
	public Stream<E> parallelStream() {
		return list.parallelStream();
	}

	@NotNull
	@Override
	public StandardIterator<E> iterator() {
		return new StandardIterator<>(list.iterator());
	}

	@Override
	public void forEach(Consumer<? super E> consumer) {
		list.forEach(consumer);
	}

	@Override
	public boolean add(E e) {
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean addAll(@NotNull Collection<? extends E> c) {
		return list.addAll(c);
	}

	@Override
	public boolean removeAll(@NotNull Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(@NotNull Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return list.removeIf(filter);
	}
}
