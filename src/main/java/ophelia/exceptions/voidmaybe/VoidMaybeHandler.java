package ophelia.exceptions.voidmaybe;

import ophelia.exceptions.StackedException;
import ophelia.function.ExceptionalRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

import static ophelia.util.ListUtils.first;
import static ophelia.util.ListUtils.last;

/**
 * @author Steven Weston
 */
public interface VoidMaybeHandler {

	@Nullable
	Exception getException();

	boolean isSuccess();

	void throwOnFailure() throws Exception;

	<F extends Exception> void throwMappedFailure(@NotNull Function<StackedException, F> exceptionTransformer) throws F;

	default void throwFirstFailure() throws Exception {
		//noinspection ThrowableResultOfMethodCallIgnored
		throwMappedFailure(e -> first(e.getExceptions()));
	}

	default void throwLastFailure() throws Exception {
		//noinspection ThrowableResultOfMethodCallIgnored
		throwMappedFailure(e -> last(e.getExceptions()));
	}

	default <E extends Exception> void throwInstead(@NotNull Supplier<E> supplier) throws E {
		//noinspection ThrowableResultOfMethodCallIgnored
		throwMappedFailure(e -> supplier.get());
	}

	@NotNull VoidMaybeHandler tryAgain(@NotNull ExceptionalRunnable<Exception> runnable);
}
