package ophelia.collections.bag;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.stream.Collector;

import static java.util.stream.Collectors.joining;

public interface BagUtils {

	@NotNull
	static <E> String presentBag(
			@NotNull final BaseIntegerBag<E> bag,
			@NotNull final BiFunction<E, Integer, String> elementPresenter,
			@NotNull final Collector<CharSequence, ?, String> surplusCollector,
			@NotNull final Collector<CharSequence, ?, String> lackingCollector
			) {
		return bag.getSurplusItems().stream()
				.map(p -> elementPresenter.apply(p.getLeft(), p.getRight()))
				.collect(surplusCollector)
			+ bag.getLackingItems().stream()
				.map(p -> elementPresenter.apply(p.getLeft(), -p.getRight()))
				.collect(lackingCollector);
	}
	@NotNull
	static <E> String presentBag(
			@NotNull final BaseIntegerBag<E> bag,
			@NotNull final BiFunction<E, Integer, String> elementPresenter,
			@NotNull final String surplusPrefix,
			@NotNull final String lackingPrefix
	) {
		return presentBag(
				bag,
				elementPresenter,
				joining(",", surplusPrefix, ""),
				joining(",", lackingPrefix, "")
		);
	}
}
