package ophelia.collections.iterator;

/**
 * @author Steven Weston
 */
public interface ModifiableIterator<E> extends BaseIterator<E> {

	/**
	 * See {@link java.util.Iterator#remove()}
	 */
	void remove();
}
