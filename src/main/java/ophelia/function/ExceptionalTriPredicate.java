package ophelia.function;

import org.jetbrains.annotations.NotNull;

/**
 * Like {@link TriPredicate}, but can throw an exception.
 */
@FunctionalInterface
public interface ExceptionalTriPredicate<R, S, T, E extends Exception> {
	boolean test(R r, S s, T t) throws E;

	@NotNull
	default ExceptionalTriPredicate<R, S, T, E> and(@NotNull TriPredicate<? super R, ? super S, ? super T> other) {
		return (r, s, t) -> test(r, s, t) && other.test(r, s, t);
	}

	@NotNull
	default ExceptionalTriPredicate<R, S, T, E> and(@NotNull ExceptionalTriPredicate<? super R, ? super S, ? super T, ? extends E> other) {
		return (r, s, t) -> test(r, s, t) && other.test(r, s, t);
	}

	@NotNull
	default ExceptionalTriPredicate<R, S, T, E> negate() {
		return (r, s, t) -> !test(r, s, t);
	}

	@NotNull
	default ExceptionalTriPredicate<R, S, T, E> or(@NotNull TriPredicate<? super R, ? super S, ? super T> other) {
		return (r, s, t) -> test(r, s, t) || other.test(r, s, t);
	}

	@NotNull
	default ExceptionalTriPredicate<R, S, T, E> or(@NotNull ExceptionalTriPredicate<? super R, ? super S, ? super T, ? extends E> other) {
		return (r, s, t) -> test(r, s, t) || other.test(r, s, t);
	}
}
