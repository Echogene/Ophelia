package ophelia.exceptions.maybe;

import ophelia.util.function.ExceptionalFunction;
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
				R r = function.apply(d);
				return new Success<>(r);
			} catch(Exception e) {
				//noinspection unchecked
				return new Failure<>((E) e);
			}
		};
	}
}
