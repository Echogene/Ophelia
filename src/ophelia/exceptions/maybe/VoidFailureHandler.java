package ophelia.exceptions.maybe;

import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
public interface VoidFailureHandler<E extends Exception> {

	void consumeOnFailure(Consumer<E> exceptionHandler);

	void throwOnFailure() throws E;

	void ignoreOnFailure();
}
