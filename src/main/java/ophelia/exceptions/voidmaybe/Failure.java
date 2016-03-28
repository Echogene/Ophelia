package ophelia.exceptions.voidmaybe;

import ophelia.exceptions.StackedException;
import ophelia.function.ExceptionalRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * @author Steven Weston
 */
class Failure implements VoidMaybe {

	private final StackedException exception;

	Failure(Exception exception) {
		this.exception = new StackedException(exception);
	}

	@Nullable
	@Override
	public Exception getException() {
		return exception;
	}

	@Override
	public boolean isSuccess() {
		return false;
	}

	@Override
	public void throwOnFailure() throws Exception {
		throw exception;
	}

	@Override
	public <F extends Exception> void throwMappedFailure(@NotNull Function<StackedException, F> exceptionTransformer) throws F {
		throw exceptionTransformer.apply(exception);
	}

	@NotNull
	@Override
	public VoidMaybeHandler tryAgain(@NotNull ExceptionalRunnable<Exception> runnable) {
		try {
			runnable.run();
			return new Success();
		} catch (Exception e) {
			exception.addException(e);
			return this;
		}
	}
}
