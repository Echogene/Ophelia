package ophelia.collections.bag;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public interface BagUtils {

	String NOTHING = "nothing";

	@NotNull
	static <E> String presentBag(
			@NotNull final BaseIntegerBag<E> bag,
			@NotNull final BiFunction<E, Integer, String> elementPresenter,
			@NotNull final Collector<CharSequence, ?, String> surplusCollector,
			@NotNull final Collector<CharSequence, ?, String> lackingCollector,
			@NotNull final String emptyName
	) {
		BaseIntegerBag<E> surplusItems = bag.getSurplusItems();
		final String surplusString;
		if (surplusItems.isEmpty()) {
			surplusString = Stream.of(emptyName).collect(surplusCollector);
		} else {
			surplusString = surplusItems.stream()
					.map(p -> elementPresenter.apply(p.getLeft(), p.getRight()))
					.collect(surplusCollector);
		}

		BaseIntegerBag<E> lackingItems = bag.getLackingItems();
		final String lackingString;
		if (lackingItems.isEmpty()) {
			lackingString = Stream.of(emptyName).collect(lackingCollector);
		} else {
			lackingString = lackingItems.stream()
					.map(p -> elementPresenter.apply(p.getLeft(), -p.getRight()))
					.collect(lackingCollector);
		}
		return surplusString + lackingString;
	}

	@NotNull
	static <E> String presentBag(
			@NotNull final BaseIntegerBag<E> bag,
			@NotNull final BiFunction<E, Integer, String> elementPresenter,
			@NotNull final Collector<CharSequence, ?, String> surplusCollector,
			@NotNull final Collector<CharSequence, ?, String> lackingCollector
	) {
		return presentBag(bag, elementPresenter, surplusCollector, lackingCollector, NOTHING);
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
				joining(",", lackingPrefix, ""),
				NOTHING
		);
	}
}
