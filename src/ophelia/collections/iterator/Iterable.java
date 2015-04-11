package ophelia.collections.iterator;

import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface Iterable<E, I extends BaseIterator<E>> {

	/**
	 * See {@link java.lang.Iterable#iterator()}
	 */
	@NotNull
	I iterator();
}
