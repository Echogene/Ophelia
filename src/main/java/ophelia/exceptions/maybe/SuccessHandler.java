package ophelia.exceptions.maybe;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
public interface SuccessHandler<T> {

	@NotNull VoidFailureHandler consumeOnSuccess(@NotNull Consumer<T> consumer);

	@NotNull FailureHandler<T> returnOnSuccess();
}
