package ophelia.exceptions.voidmaybe;

import ophelia.exceptions.StackedException;
import ophelia.exceptions.maybe.VoidFailureHandler;
import ophelia.function.ExceptionalRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author Steven Weston
 */
public interface VoidMaybeHandler extends VoidFailureHandler {

	boolean isSuccess();

	@NotNull
	Optional<StackedException> getException();

	@NotNull VoidMaybeHandler tryAgain(@NotNull ExceptionalRunnable<Exception> runnable);
}
