package ophelia.exceptions.voidmaybe;

import ophelia.exceptions.StackedException;
import ophelia.function.ExceptionalRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static ophelia.exceptions.voidmaybe.Success.SUCCESS;

/**
 * @author Steven Weston
 */
class Failure implements VoidMaybe {

	private final StackedException exception;

	Failure(Exception exception) {
		this.exception = new StackedException(exception);
	}

	@NotNull
	@Override
	public Optional<StackedException> getException() {
		return Optional.of(exception);
	}

	@Override
	public void consumeOnFailure(@NotNull Consumer<StackedException> exceptionHandler) {
		exceptionHandler.accept(exception);
	}

	@Override
	public boolean isSuccess() {
		return false;
	}

	@Override
	public void throwOnFailure() throws StackedException {
		throw exception;
	}

	@Override
	public void ignoreOnFailure() {}

	@Override
	public <F extends Exception> void throwMappedFailure(@NotNull Function<StackedException, F> exceptionTransformer) throws F {
		throw exceptionTransformer.apply(exception);
	}

	@NotNull
	@Override
	public VoidMaybeHandler tryAgain(@NotNull ExceptionalRunnable<Exception> runnable) {
		try {
			runnable.run();
			return SUCCESS;
		} catch (Exception e) {
			exception.addException(e);
			return this;
		}
	}
}
