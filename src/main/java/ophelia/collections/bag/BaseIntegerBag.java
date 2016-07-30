package ophelia.collections.bag;

import ophelia.tuple.Pair;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link BaseBag} that can contain integer numbers of copies of elements.
 * @param <E>
 */
public interface BaseIntegerBag<E> extends BaseBag<E, Integer> {
	@NotNull
	@Override
	default Integer size() {
		return stream()
				.mapToInt(Pair::getRight)
				.sum();
	}
}
