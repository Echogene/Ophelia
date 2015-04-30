package ophelia.collections.iterator;

import org.jetbrains.annotations.NotNull;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A generic version of {@link Iterable}.
 * @author Steven Weston
 */
public interface BaseIterable<E, I extends BaseIterator<E>> {

	/**
	 * See {@link Iterable#iterator()}
	 */
	@NotNull
	I iterator();

	/**
	 * See {@link Iterable#forEach(Consumer)}
	 */
	void forEach(Consumer<? super E> consumer);

	/**
	 * See {@link Iterable#spliterator()}
	 */
	Spliterator<E> spliterator();
}
