package ophelia.exceptions.maybe;

import ophelia.util.function.ExceptionalFunction;
import ophelia.util.function.ExceptionalSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
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
	static <D> Maybe<D, NullPointerException> notNull(@Nullable D d) {
		if (d != null) {
			return new Success<>(d);
		} else {
			return new Failure<>(new NullPointerException());
		}
	}
}
