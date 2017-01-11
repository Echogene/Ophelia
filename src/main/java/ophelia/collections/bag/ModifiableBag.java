package ophelia.collections.bag;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link BaseBag} that can have elements added and removed from it.
 * @param <E> the type of the elements contained in the {@code Bag}
 * @param <N> the type of number that the multiplicities of the elements can take
 */
public interface ModifiableBag<E, N extends Number> extends BaseBag<E, N> {

	/**
	 * Add or take a number of copies of an element to or from this bag.
	 *
	 * @param element the element in the bag we want to add or remove some copies of
	 * @param number the number of elements we should put into the bag (positive) or take out of the bag (negative)
	 * @return the previous number of copies of the given element in the bag
	 */
	@NotNull
	N modifyNumberOf(@NotNull E element, @NotNull N number);

	/**
	 * Add an element to this bag.
	 * @param element the element we want to add one copy of to the bag
	 * @return the previous number of copies of the given element in the bag before the new one was been added
	 */
	@NotNull
	N addOne(@NotNull E element);

	/**
	 * Take an element from this bag.
	 * @param element the element we want to take one copy of from the bag
	 * @return the previous number of copies of the given element in the bag before the old one was taken away
	 */
	@NotNull
	N takeOne(@NotNull E element);

	/**
	 * @param toMergeIn another bag whose numbers of copies of elements should be merged into this one
	 */
	default void merge(@NotNull BaseBag<E, N> toMergeIn) {
		toMergeIn.forEach(this::modifyNumberOf);
	}

	/**
	 * @param subtrahend another bag whose numbers of copies of elements should be subtracted from this one
	 */
	void subtract(@NotNull BaseBag<E, N> subtrahend);
}
