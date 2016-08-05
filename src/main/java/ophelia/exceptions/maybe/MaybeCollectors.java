package ophelia.exceptions.maybe;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static ophelia.util.FunctionUtils.checkSecondThenMaybeConsume;
import static ophelia.util.FunctionUtils.mapSecondThenConsume;

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
}
