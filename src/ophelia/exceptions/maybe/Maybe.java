package ophelia.exceptions.maybe;

import ophelia.util.function.ExceptionalFunction;
import ophelia.util.function.ExceptionalSupplier;
import org.jetbrains.annotations.NotNull;

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
	static <D, E extends Exception> Maybe<D, E> maybe(ExceptionalSupplier<D, E> supplier) {
		try {
			return new Success<>(supplier.get());
		} catch (Exception e) {
			//noinspection unchecked
			return new Failure<>((E) e);
		}
	}

	@NotNull
	static <D, E extends Exception> Maybe<D, E> maybe(D d) {
		return new Success<>(d);
	}
}
