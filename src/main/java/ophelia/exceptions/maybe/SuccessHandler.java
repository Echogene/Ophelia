package ophelia.exceptions.maybe;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
public interface SuccessHandler<T> {

	@NotNull
	VoidFailureHandler consumeOnSuccess(@NotNull Consumer<T> consumer);

	@NotNull
	default VoidFailureHandler ignoreOnSuccess() {
		return consumeOnSuccess(t -> {});
	}

	@NotNull
	FailureHandler<T> returnOnSuccess();

	@NotNull
	<S> FailureHandler<S> mapOnSuccess(@NotNull Function<T, S> map);

	boolean isSuccess();
}
