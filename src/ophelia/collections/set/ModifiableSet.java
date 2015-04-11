package ophelia.collections.set;

import ophelia.collections.ModifiableCollection;
import ophelia.collections.iterator.BaseIterator;

/**
 * @author Steven Weston
 */
public interface ModifiableSet<E, I extends BaseIterator<E>> extends BaseSet<E, I>, ModifiableCollection<E> {

}
