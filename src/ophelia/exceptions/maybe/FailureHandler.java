package ophelia.exceptions.maybe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * @author Steven Weston
 */
public interface FailureHandler<S, E extends Exception> {

	S mapOnFailure(@NotNull Function<E, S> exceptionHandler);

	S throwOnFailure() throws E;

	@NotNull S defaultOnFailure(@NotNull S defaultValue);

	@Nullable S nullOnFailure();
}
