package ophelia.collections.bag;

import ophelia.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

public interface BaseBag<E, N extends Number> {

	/**
	 * @return the number of copies of the given element in this bag.  Returns zero if this bag has never seen the
	 * given element before (<i>i.e.</i> it once contained some copies of the element but they have since been removed).
	 */
	@NotNull
	N getNumberOf(@NotNull E element);

	/**
	 * @return a stream across all the elements that have a non-zero number of copies in this bag.
	 */
	@NotNull
	Stream<Pair<E, N>> stream();

	/**
	 * Consume all the elements alongside their non-zero numbers of copies in this bag.  Elements that have had copies
	 * in this bag but they have since all been removed, will not be consumed.
	 */
	void forEach(@NotNull BiConsumer<E, N> consumer);
}
