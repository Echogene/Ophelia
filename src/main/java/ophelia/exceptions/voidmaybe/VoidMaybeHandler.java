package ophelia.exceptions.voidmaybe;

import ophelia.exceptions.maybe.VoidFailureHandler;
import ophelia.function.ExceptionalRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface VoidMaybeHandler extends VoidFailureHandler {

	boolean isSuccess();

	@NotNull VoidMaybeHandler tryAgain(@NotNull ExceptionalRunnable<Exception> runnable);
}
