package ophelia.tuple;

import ophelia.util.function.ExceptionalBiConsumer;
import ophelia.util.function.ExceptionalBiFunction;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @author Steven Weston
 */
public class PairUtil {

	public static <S, T> void consume(
			@NotNull BiConsumer<? super S, ? super T> consumer,
			@NotNull Pair<S, T> pair
	) {
		consumer.accept(pair.getLeft(), pair.getRight());
	}

	public static <S, T, E extends Exception> void consume(
			@NotNull ExceptionalBiConsumer<? super S, ? super T, E> consumer,
			@NotNull Pair<S, T> pair
	) throws E {

		consumer.accept(pair.getLeft(), pair.getRight());
	}

	public static <S, T, R> R apply(
			@NotNull BiFunction<? super S, ? super T, R> function,
			@NotNull Pair<S, T> pair
	) {
		return function.apply(pair.getLeft(), pair.getRight());
	}

	public static <S, T, R, E extends Exception> R apply(
			@NotNull ExceptionalBiFunction<? super S, ? super T, R, E> function,
			@NotNull Pair<S, T> pair
	) throws E {

		return function.apply(pair.getLeft(), pair.getRight());
	}
}
