package ophelia.exceptions.maybe;

import ophelia.exceptions.StackedException;
import ophelia.function.ExceptionalFunction;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
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

	@NotNull
	Optional<StackedException> getException();

	@NotNull
	<S> Maybe<S> map(@NotNull ExceptionalFunction<T, S, ?> map);
}
