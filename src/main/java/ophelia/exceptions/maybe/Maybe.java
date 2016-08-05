package ophelia.exceptions.maybe;

import ophelia.exceptions.AmbiguityException;
import ophelia.exceptions.CollectedException;
import ophelia.function.ExceptionalBiFunction;
import ophelia.function.ExceptionalFunction;
import ophelia.function.ExceptionalSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Like {@link Optional}, but stores the exception in case of failure for future processing.
 *
 * <p>
 * ♪Hey, I just met you,<br>
 * And this is crazy<br>
 * But here's my number<br>
 * So call me {@code Maybe}♪</p>
 * @author Steven Weston
 */
public interface Maybe<D> extends SuccessHandler<D> {

	/**
	 * Convert an exceptional supplier into a supplier of maybes.
	 */
	@NotNull
	static <T> Supplier<Maybe<T>> wrap(
			@NotNull ExceptionalSupplier<? extends T, ? extends Exception> supplier
	) {
		return () -> {
			try {
				return new Success<>(supplier.get());
			} catch (Exception e) {
				return new Failure<>(e);
			}
		};
	}

	/**
	 * Convert an exceptional function into a function of maybes.
	 */
	@NotNull
	static <D, R> Function<D, Maybe<R>> wrap(
			@NotNull ExceptionalFunction<D, R, ? extends Exception> function
	) {
		return d -> {
			try {
				return new Success<>(function.apply(d));
			} catch (Exception e) {
				return new Failure<>(e);
			}
		};
	}

	/**
	 * Convert an exceptional bifunction into a bifunction of maybes.
	 */
	@NotNull
	static <D, F, R> BiFunction<D, F, Maybe<R>> wrap(
			@NotNull ExceptionalBiFunction<D, F, R, ? extends Exception> function
	) {
		return (d, f) -> {
			try {
				return new Success<>(function.apply(d, f));
			} catch (Exception e) {
				return new Failure<>(e);
			}
		};
	}

	/**
	 * Get the supplied value of the given exceptional supplier by wrapping it in a maybe in case it fails.
	 */
	@NotNull
	static <D> Maybe<D> maybe(@NotNull ExceptionalSupplier<? extends D, ? extends Exception> supplier) {
		try {
			return new Success<>(supplier.get());
		} catch (Exception e) {
			return new Failure<>(e);
		}
	}

	/**
	 * Create a maybe that is successful if and only if the given argument is not null.
	 * {@see Optional#ofNullable(Object)}
	 */
	@NotNull
	static <D> Maybe<D> notNull(@Nullable D d) {
		if (d != null) {
			return new Success<>(d);
		} else {
			return new Failure<>(new NullPointerException());
		}
	}

	/**
	 * Create maybe that is a successful value.
	 */
	@NotNull
	static <R> Maybe<R> success(@Nullable R r) {
		return new Success<>(r);
	}

	/**
	 * Create maybe that is an unsuccessful value.
	 */
	@NotNull
	static <R> Maybe<R> failure(@NotNull Exception e) {
		return new Failure<>(e);
	}

	/**
	 * Create a maybe that represents failed uniqueness.
	 */
	@NotNull
	static <R> Maybe<R> ambiguity(@NotNull Collection<R> collection) {
		return failure(new AmbiguityException("There were multiple elements: {0}", collection));
	}

	/**
	 * Create a maybe that wraps multiple failures.
	 */
	@NotNull
	static <R> Maybe<R> multipleFailures(@NotNull List<Exception> exceptions) {
		return failure(new CollectedException(exceptions));
	}
}
