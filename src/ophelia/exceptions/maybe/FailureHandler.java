package ophelia.exceptions.maybe;

import ophelia.util.function.ExceptionalSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * @author Steven Weston
 */
public interface FailureHandler<S, E extends Exception> {

	S handleFailure(@NotNull Function<E, S> exceptionHandler);

	S throwFailure() throws E;

	<F extends Exception> S throwMappedFailure(@NotNull Function<E, F> exceptionTransformer) throws F;

	@NotNull S defaultOnFailure(@NotNull S defaultValue);

	@Nullable S nullOnFailure();

	@NotNull Maybe<S, E> tryAgain(@NotNull ExceptionalSupplier<S, E> supplier);
}
