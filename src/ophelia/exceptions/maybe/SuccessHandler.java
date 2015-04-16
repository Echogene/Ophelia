package ophelia.exceptions.maybe;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
public interface SuccessHandler<T, E extends Exception> {

	VoidFailureHandler<E> consumeOnSuccess(Consumer<T> consumer);

	FailureHandler<T, E> returnOnSuccess();

	<R> FailureHandler<R, E> mapOnSuccess(Function<T, R> function);
}
