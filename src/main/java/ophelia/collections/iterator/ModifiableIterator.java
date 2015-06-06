package ophelia.collections.iterator;

/**
 * An iterator that can remove elements while it is being used.
 * @author Steven Weston
 */
public interface ModifiableIterator<E> extends BaseIterator<E> {

	/**
	 * See {@link java.util.Iterator#remove()}
	 */
	void remove();
}
