package ophelia.collections.set;

import ophelia.collections.IntegerFiniteCollection;
import ophelia.collections.iterator.BaseIterable;
import ophelia.collections.iterator.BaseIterator;

public interface IntegerFiniteSet<E, I extends BaseIterator<E>> extends BaseSet<E>, IntegerFiniteCollection<E, I> {
}
