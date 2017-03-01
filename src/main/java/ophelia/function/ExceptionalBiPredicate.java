package ophelia.function;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;

/**
 * Like {@link BiPredicate}, but can throw an exception.
 */
@FunctionalInterface
public interface ExceptionalBiPredicate<S, T, E extends Exception> {
	boolean test(S s, T t) throws E;

	@NotNull
	default ExceptionalBiPredicate<S, T, E> and(@NotNull BiPredicate<? super S, ? super T> other) {
		return (s, t) -> test(s, t) && other.test(s, t);
	}

	@NotNull
	default ExceptionalBiPredicate<S, T, E> and(@NotNull ExceptionalBiPredicate<? super S, ? super T, ? extends E> other) {
		return (s, t) -> test(s, t) && other.test(s, t);
	}

	@NotNull
	default ExceptionalBiPredicate<S, T, E> negate() {
		return (s, t) -> !test(s, t);
	}

	@NotNull
	default ExceptionalBiPredicate<S, T, E> or(@NotNull BiPredicate<? super S, ? super T> other) {
		return (s, t) -> test(s, t) || other.test(s, t);
	}

	@NotNull
	default ExceptionalBiPredicate<S, T, E> or(@NotNull ExceptionalBiPredicate<? super S, ? super T, ? extends E> other) {
		return (s, t) -> test(s, t) || other.test(s, t);
	}
}
