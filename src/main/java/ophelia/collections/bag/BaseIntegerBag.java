package ophelia.collections.bag;

import ophelia.tuple.Pair;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link BaseBag} that can contain integer numbers of copies of elements.
 * @param <E> the type of the elements contained in the {@code Bag}
 */
public interface BaseIntegerBag<E> extends BaseBag<E, Integer> {

	@Override
	default boolean hasLackingItems() {
		return stream()
				.mapToInt(Pair::getRight)
				.anyMatch(i -> i < 0);
	}

	@NotNull
	@Override
	BaseIntegerBag<E> getLackingItems();

	@Override
	default boolean hasSurplusItems() {
		return stream()
				.mapToInt(Pair::getRight)
				.anyMatch(i -> i > 0);
	}

	@NotNull
	@Override
	BaseIntegerBag<E> getSurplusItems();

	@NotNull
	@Override
	BaseIntegerBag<E> getDifference(@NotNull BaseBag<? extends E, ? extends Integer> subtrahend);

	@NotNull
	@Override
	BaseIntegerBag<E> getSum(@NotNull BaseBag<? extends E, ? extends Integer> addend);

	@NotNull
	@Override
	default Integer size() {
		return stream()
				.mapToInt(Pair::getRight)
				.sum();
	}

	@NotNull
	@Override
	BaseIntegerBag<E> getInverse();
}
