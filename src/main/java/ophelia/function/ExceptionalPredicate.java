package ophelia.function;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Like {@link Predicate}, but can throw an exception.
 */
@FunctionalInterface
public interface ExceptionalPredicate<T, E extends Exception> {
	boolean test(T t) throws E;

	@NotNull
	default ExceptionalPredicate<T, E> and(@NotNull Predicate<? super T> other) {
		return t -> test(t) && other.test(t);
	}

	@NotNull
	default ExceptionalPredicate<T, E> and(@NotNull ExceptionalPredicate<? super T, ? extends E> other) {
		return t -> test(t) && other.test(t);
	}

	@NotNull
	default ExceptionalPredicate<T, E> negate() {
		return t -> !test(t);
	}

	@NotNull
	default ExceptionalPredicate<T, E> or(@NotNull Predicate<? super T> other) {
		return t -> test(t) || other.test(t);
	}

	@NotNull
	default ExceptionalPredicate<T, E> or(@NotNull ExceptionalPredicate<? super T, ? extends E> other) {
		return t -> test(t) || other.test(t);
	}
}
