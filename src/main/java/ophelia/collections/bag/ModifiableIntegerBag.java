package ophelia.collections.bag;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link ModifiableIntegerBag} that can have {@link Integer} multiplicities of its elements.
 * @param <E> the type of the elements contained in the {@code Bag}
 */
public interface ModifiableIntegerBag<E> extends ModifiableBag<E, Integer>, BaseIntegerBag<E> {

	@NotNull
	@Override
	default Integer addOne(@NotNull E element) {
		return modifyNumberOf(element, 1);
	}

	@NotNull
	@Override
	default Integer takeOne(@NotNull E element) {
		return modifyNumberOf(element, -1);
	}

	@Override
	default void subtract(@NotNull BaseBag<E, Integer> subtrahend) {
		subtrahend.forEach((e, n) -> modifyNumberOf(e, -n));
	}
}
