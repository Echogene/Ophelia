package ophelia.exceptions.maybe;

import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
public interface VoidFailureHandler {

	void consumeOnFailure(Consumer<StackedException> exceptionHandler);

	void throwOnFailure() throws StackedException;

	void ignoreOnFailure();
}
