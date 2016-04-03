package ophelia.exceptions.maybe;

import ophelia.exceptions.StackedException;
import ophelia.function.ExceptionalSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Steven Weston
 */
public interface FailureHandler<S> {

	S resolveFailure(@NotNull Function<StackedException, S> exceptionHandler);

	S throwAllFailures() throws StackedException;

	<F extends Exception> S throwMappedFailure(@NotNull Function<StackedException, F> exceptionTransformer) throws F;

	default <E extends Exception> S throwInstead(@NotNull Supplier<E> supplier) throws E {
		//noinspection ThrowableResultOfMethodCallIgnored
		return throwMappedFailure(e -> supplier.get());
	}

	@Nullable S defaultOnFailure(@NotNull S defaultValue);

	@Nullable S nullOnFailure();

	@NotNull FailureHandler<S> tryAgain(@NotNull ExceptionalSupplier<S, Exception> supplier);
}
