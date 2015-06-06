package ophelia.exceptions.maybe;

import ophelia.function.ExceptionalFunction;
import ophelia.function.ExceptionalSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

	@NotNull
	static <D, R> Function<D, Maybe<R>> wrapOutput(
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

	@NotNull
	static <S, T> Function<Maybe<S>, Maybe<T>> wrap(@NotNull Function<S, T> function) {
		return maybe -> transform(maybe, function);
	}

	@NotNull
	static <S, T> Maybe<T> transform(@NotNull Maybe<S> maybe, @NotNull Function<S, T> function) {
		try {
			S s = maybe.returnOnSuccess().throwAllFailures();
			return new Success<>(function.apply(s));

		} catch (StackedException e) {
			return new Failure<>(e);
		}
	}

	/**
	 * @return a stream containing each of the outputs of the given function for which it did not throw an exception
	 */
	@NotNull
	static <S, T> Stream<T> filterPassingValues(Stream<S> source, ExceptionalFunction<S, T, ? extends Exception> map) {
		return source.map(wrapOutput(map))
				.map(maybe -> maybe.returnOnSuccess().nullOnFailure())
				.filter(t -> t != null);
	}

	@NotNull
	static <D> Maybe<D> maybe(@NotNull ExceptionalSupplier<? extends D, ? extends Exception> supplier) {
		try {
			return new Success<>(supplier.get());
		} catch (Exception e) {
			return new Failure<>(e);
		}
	}

	@NotNull
	static <D> Maybe<D> maybe(@Nullable D d) {
		return new Success<>(d);
	}

	@NotNull
	static <D> Maybe<D> maybePresent(
			@NotNull Optional<? extends D> optional,
			@NotNull Supplier<? extends Exception> exceptionSupplier
	) {
		if (optional.isPresent()) {
			return new Success<>(optional.get());
		} else {
			return new Failure<>(exceptionSupplier.get());
		}
	}

	@NotNull
	static <D> Maybe<D> maybePresent(
			@NotNull Optional<? extends D> optional
	) {
		return maybePresent(optional, NullPointerException::new);
	}

	@NotNull
	static <D> Maybe<D> notNull(@Nullable D d) {
		if (d != null) {
			return new Success<>(d);
		} else {
			return new Failure<>(new NullPointerException());
		}
	}
}
