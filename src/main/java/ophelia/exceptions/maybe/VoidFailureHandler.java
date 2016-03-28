package ophelia.exceptions.maybe;

import ophelia.exceptions.StackedException;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
public interface VoidFailureHandler {

	<F extends Exception> void throwMappedFailure(@NotNull Function<StackedException, F> exceptionTransformer) throws F;

	void consumeOnFailure(@NotNull Consumer<StackedException> exceptionHandler);

	void throwOnFailure() throws StackedException;

	void ignoreOnFailure();
}
