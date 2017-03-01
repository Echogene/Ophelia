package ophelia.function;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;

/**
 * Like {@link BiPredicate} but can test three things at once.
 */
@FunctionalInterface
public interface TriPredicate<R, S, T> {

	boolean test(R r, S s, T t);

	@NotNull
	default TriPredicate<R, S, T> and(@NotNull TriPredicate<? super R, ? super S, ? super T> other) {
		return (r, s, t) -> test(r, s, t) && other.test(r, s, t);
	}

	@NotNull
	default TriPredicate<R, S, T> negate() {
		return (r, s, t) -> !test(r, s, t);
	}

	@NotNull
	default TriPredicate<R, S, T> or(@NotNull TriPredicate<? super R, ? super S, ? super T> other) {
		return (r, s, t) -> test(r, s, t) || other.test(r, s, t);
	}
}
