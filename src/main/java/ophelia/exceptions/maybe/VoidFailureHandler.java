package ophelia.exceptions.maybe;

import ophelia.exceptions.StackedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Steven Weston
 */
public interface VoidFailureHandler {

	<F extends Exception> void throwMappedFailure(@NotNull Function<StackedException, F> exceptionTransformer) throws F;

	default <E extends Exception> void throwInstead(@NotNull Supplier<E> supplier) throws E {
		//noinspection ThrowableResultOfMethodCallIgnored
		throwMappedFailure(e -> supplier.get());
	}

	@Nullable
	StackedException getException();

	void consumeOnFailure(@NotNull Consumer<StackedException> exceptionHandler);

	void throwOnFailure() throws StackedException;

	void ignoreOnFailure();
}
