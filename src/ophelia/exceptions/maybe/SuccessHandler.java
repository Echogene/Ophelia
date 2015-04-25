package ophelia.exceptions.maybe;

import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
public interface SuccessHandler<T> {

	VoidFailureHandler consumeOnSuccess(Consumer<T> consumer);

	FailureHandler<T> returnOnSuccess();
}
