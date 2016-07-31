package ophelia.collections.bag;

import ophelia.tuple.Pair;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link BaseBag} that can contain integer numbers of copies of elements.
 * @param <E>
 */
public interface BaseIntegerBag<E> extends BaseBag<E, Integer> {

	@Override
	default boolean isLacking() {
		return stream()
				.mapToInt(Pair::getRight)
				.anyMatch(i -> i < 0);
	}

	@NotNull
	@Override
	BaseIntegerBag<E> getDifference(@NotNull BaseBag<? extends E, ? extends Integer> subtrahend);

	@NotNull
	@Override
	default Integer size() {
		return stream()
				.mapToInt(Pair::getRight)
				.sum();
	}
}
