package ophelia.collections.bag;

import org.jetbrains.annotations.NotNull;

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
}
