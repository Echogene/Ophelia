package ophelia.collections.iterator;

/**
 * @author Steven Weston
 */
public interface RemovableIterator<E> extends BaseIterator<E> {

	/**
	 * See {@link java.util.Iterator#remove()}
	 */
	void remove();
}
