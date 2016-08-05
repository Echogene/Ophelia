package ophelia.exceptions.maybe;

import ophelia.collections.list.UnmodifiableList;
import ophelia.exceptions.CollectedException;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;
import static ophelia.exceptions.maybe.Maybe.*;
import static ophelia.util.FunctionUtils.checkSecondThenMaybeConsume;
import static ophelia.util.FunctionUtils.mapSecondThenConsume;
import static ophelia.util.MapUtils.updateSetBasedMap;

public interface MaybeCollectors {

	@NotNull
	static <T, C extends Collection<T>> Collector<Maybe<T>, C, C> toCollectionOfSuccesses(@NotNull Supplier<C> supplier) {
		return toSuccesses(
				supplier,
				Collection::add,
				(left, right) -> { left.addAll(right); return left; },
				Function.identity()
		);
	}

	@NotNull
	static <T, A, R> Collector<Maybe<T>, A, R> toSuccesses(
			@NotNull Supplier<A> supplier,
			@NotNull BiConsumer<A, T> accumulator,
			@NotNull BinaryOperator<A> combiner,
			@NotNull Function<A, R> finisher
	) {
		return Collector.of(
				supplier,
				checkSecondThenMaybeConsume(
						Maybe::isSuccess,
						mapSecondThenConsume(
								m -> m.returnOnSuccess().nullOnFailure(),
								accumulator
						)
				),
				combiner,
				finisher
		);
	}

	@NotNull
	static <T> Collector<Maybe<T>, List<T>, List<T>> toListOfSuccesses() {
		return toCollectionOfSuccesses(ArrayList::new);
	}

	@NotNull
	static <T> Collector<Maybe<T>, Set<T>, Set<T>> toSetOfSuccesses() {
		return toCollectionOfSuccesses(HashSet::new);
	}

	@NotNull
	static <T> Collector<Maybe<T>, Map<Boolean, Set<Maybe<T>>>, Maybe<T>> toUniqueSuccess() {
		return Collector.of(
				HashMap::new,
				(map, m) -> updateSetBasedMap(map, m.isSuccess(), m),
				(left, right) -> {
					left.putAll(right);
					return left;
				},
				map -> {
					Set<Maybe<T>> successes = map.getOrDefault(true, emptySet());
					Set<Maybe<T>> failures = map.getOrDefault(false, emptySet());
					if (successes.isEmpty()) {
						if (failures.isEmpty()) {
							return failure(new NoSuchElementException());

						} else if (failures.size() > 1) {
							return multipleFailures(
									failures.stream()
											.map(SuccessHandler::getException)
											.map(Optional::get)
											.map(CollectedException::flatten)
											.flatMap(UnmodifiableList::stream)
											.collect(toList())
							);
						} else {
							return failures.iterator().next();
						}
					} else if (successes.size() > 1) {
						return ambiguity(
								successes.stream()
										.map(Maybe::returnOnSuccess)
										.map(FailureHandler::nullOnFailure)
										.collect(toList())
						);
					}
					return successes.iterator().next();
				}
		);
	}
}
