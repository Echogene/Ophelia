package ophelia.collections.set;

import ophelia.collections.iterator.StandardIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Steven Weston
 */
public class HashSet<E> extends java.util.HashSet<E> implements ModifiableSet<E, StandardIterator<E>> {

	public HashSet() {
		super();
	}

	public HashSet(Collection<? extends E> c) {
		super(c);
	}

	public HashSet(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public HashSet(int initialCapacity) {
		super(initialCapacity);
	}

	@NotNull
	@Override
	public StandardIterator<E> iterator() {
		return new StandardIterator<>(super.iterator());
	}
}
