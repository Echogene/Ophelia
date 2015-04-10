package ophelia.collections.iterator;

/**
 * @author Steven Weston
 */
public interface Iterable<E, I extends BaseIterator<E>> {

	/**
	 * See {@link java.lang.Iterable#iterator()}
	 */
	I iterator();
}
