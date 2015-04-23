package ophelia.exceptions.maybe;

import ophelia.util.function.ExceptionalFunction;
import ophelia.util.function.ExceptionalSupplier;
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
public interface Maybe<D, E extends Exception> extends SuccessHandler<D, E> {

	@NotNull
	static <D, R, E extends Exception> Function<D, Maybe<R, E>> wrapOutput(
			@NotNull ExceptionalFunction<D, R, E> function
	) {
		return d -> {
			try {
				return new Success<>(function.apply(d));
			} catch(Exception e) {
				//noinspection unchecked
				return new Failure<>((E) e);
			}
		};
	}

	static <S, T, E extends Exception> Stream<T> filterPassingValues(Stream<S> source, ExceptionalFunction<S, T, E> map) {
		return source.map(wrapOutput(map))
				.map(maybe -> maybe.returnOnSuccess().nullOnFailure())
				.filter(t -> t != null);
	}

	@NotNull
	static <D, E extends Exception> Maybe<D, E> maybe(@NotNull ExceptionalSupplier<? extends D, ? extends E> supplier) {
		try {
			return new Success<>(supplier.get());
		} catch (Exception e) {
			//noinspection unchecked
			return new Failure<>((E) e);
		}
	}

	@NotNull
	static <D, E extends Exception> Maybe<D, E> maybe(@Nullable D d) {
		return new Success<>(d);
	}

	@NotNull
	static <D, E extends Exception> Maybe<D, E> maybePresent(
			@NotNull Optional<? extends D> optional,
			@NotNull Supplier<? extends E> exceptionSupplier
	) {
		if (optional.isPresent()) {
			return new Success<>(optional.get());
		} else {
			return new Failure<>(exceptionSupplier.get());
		}
	}

	@NotNull
	static <D> Maybe<D, NullPointerException> maybePresent(
			@NotNull Optional<? extends D> optional
	) {
		return maybePresent(optional, NullPointerException::new);
	}

	@NotNull
	static <D> Maybe<D, NullPointerException> notNull(@Nullable D d) {
		if (d != null) {
			return new Success<>(d);
		} else {
			return new Failure<>(new NullPointerException());
		}
	}
}
