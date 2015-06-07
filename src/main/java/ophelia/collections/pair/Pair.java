package ophelia.collections.pair;

import ophelia.collections.IntegerFiniteCollection;
import ophelia.collections.iterator.StandardIterator;

import java.util.Collection;

/**
 * @author Steven Weston
 */
public interface Pair<E> extends IntegerFiniteCollection<E, StandardIterator<E>> {

	@Override
	default int size() {
		return 2;
	}

	@Override
	default boolean isEmpty() {
		return false;
	}

	@Override
	default boolean containsAll(Collection<?> c) {
		return c.stream().allMatch(Pair.this::contains);
	}
}
