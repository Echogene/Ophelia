package ophelia.collections.bag;

import ophelia.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * A {@code Bag} is like a {@link Set} but it can have multiple copies of each of the elements.  This interface
 * actually allows elements to have multiplicities that are negative numbers.  These elements would be considered to be
 * lacking from the {@code Bag}.
 * @param <E> the type of the elements contained in the {@code Bag}
 * @param <N> the type of number that the multiplicities of the elements can take.  Theoretically, one could implement
 *            a {@code Bag} where the elements can be in the bag a complex number of times.
 */
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
	 * @return the bag that results from taking all the copies of the elements from the subtrahend bag out of this one
	 */
	@NotNull
	BaseBag<E, N> getDifference(@NotNull BaseBag<? extends E,  ? extends N> subtrahend);

	/**
	 * Consume all the elements alongside their non-zero numbers of copies in this bag.  Elements that have had copies
	 * in this bag but they have since all been removed, will not be consumed.
	 */
	void forEach(@NotNull BiConsumer<? super E, ? super N> consumer);

	/**
	 * @return whether this bag contains no elements that have a non-zero number of copies in it.
	 */
	boolean isEmpty();

	/**
	 * @return whether this is lacking some copies of an element, <i>i.e.</i> an element in the bag has a negative
	 *         number of copies.
	 */
	boolean isLacking();

	/**
	 * @return whether this has some copies of an element, <i>i.e.</i> an element in the bag has a positive number
	 * of copies.
	 */
	boolean hasItems();

	/**
	 * @return the total number of copies of elements in this bag.
	 */
	@NotNull
	N size();

	/**
	 * @return the bag that is the additive inverse of this.  <i>i.e.</i> were you to add this bag to its inverse, you
	 * would get the empty bag.
	 */
	@NotNull
	BaseBag<E, N> getInverse();
}
