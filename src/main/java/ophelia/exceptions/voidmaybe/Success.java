package ophelia.exceptions.voidmaybe;

import ophelia.exceptions.StackedException;
import ophelia.function.ExceptionalRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
class Success implements VoidMaybe {
	@Nullable
	@Override
	public StackedException getException() {
		return null;
	}

	@Override
	public void consumeOnFailure(@NotNull Consumer<StackedException> exceptionHandler) {}

	@Override
	public boolean isSuccess() {
		return true;
	}

	@Override
	public void throwOnFailure() throws StackedException {}

	@Override
	public void ignoreOnFailure() {}

	@Override
	public <F extends Exception> void throwMappedFailure(@NotNull Function<StackedException, F> exceptionTransformer) throws F {}

	@NotNull
	@Override
	public VoidMaybeHandler tryAgain(@NotNull ExceptionalRunnable<Exception> runnable) {
		return this;
	}
}
