package ophelia.collections.list;

import ophelia.collections.iterator.StandardIterator;
import ophelia.collections.iterator.StandardListIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Steven Weston
 */
public class ArrayList<E> extends java.util.ArrayList<E> implements ModifiableList<E, StandardIterator<E>, StandardListIterator<E>> {

	public ArrayList() {
		super();
	}

	public ArrayList(Collection<? extends E> c) {
		super(c);
	}

	public ArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	@NotNull
	@Override
	public StandardIterator<E> iterator() {
		return new StandardIterator<>(super.iterator());
	}

	@NotNull
	@Override
	public StandardListIterator<E> listIterator() {
		return new StandardListIterator<>(super.listIterator());
	}

	@NotNull
	@Override
	public StandardListIterator<E> listIterator(int index) {
		return new StandardListIterator<>(super.listIterator(index));
	}
}
