package ophelia.exceptions.maybe;

import ophelia.exceptions.StackedException;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
public interface VoidFailureHandler {

	void consumeOnFailure(@NotNull Consumer<StackedException> exceptionHandler);

	void throwOnFailure() throws StackedException;

	void ignoreOnFailure();
}
