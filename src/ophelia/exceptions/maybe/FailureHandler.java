package ophelia.exceptions.maybe;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * @author Steven Weston
 */
public interface FailureHandler<S, E extends Exception> {

	S mapOnFailure(Function<E, S> exceptionHandler);

	S throwOnFailure() throws E;

	@NotNull S defaultOnFailure(@NotNull S defaultValue);

	S nullOnFailure();
}
