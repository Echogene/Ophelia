package ophelia.collections.bag;

import org.jetbrains.annotations.NotNull;

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

	@NotNull
	N addOne(@NotNull E element);

	@NotNull
	N takeOne(@NotNull E element);

	/**
	 *
	 * @param toMergeIn another bag whose numbers of copies of elements should be merged into this one
	 */
	default void merge(@NotNull BaseBag<E, N> toMergeIn) {
		toMergeIn.stream().forEach(pair -> modifyNumberOf(pair.getLeft(), pair.getRight()));
	}
}