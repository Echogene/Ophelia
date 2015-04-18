package ophelia.exceptions.maybe;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
class Success<R, E extends Exception> implements Maybe<R, E> {

	private final R value;

	Success(R r) {
		this.value = r;
	}

	@Override
	public VoidFailureHandler<E> consumeOnSuccess(Consumer<R> consumer) {
		return new VoidFailureHandler<E>() {
			@Override
			public void consumeOnFailure(Consumer<E> exceptionHandler) {
				consumer.accept(value);
			}

			@Override
			public void throwOnFailure() throws E {
				consumer.accept(value);
			}

			@Override
			public void ignoreOnFailure() {
				consumer.accept(value);
			}
		};
	}

	@Override
	public FailureHandler<R, E> returnOnSuccess() {
		return new FailureHandler<R, E>() {
			@Override
			public R handleFailure(@NotNull Function<E, R> exceptionHandler) {
				return value;
			}

			@Override
			public R throwFailure() throws E {
				return value;
			}

			@Override
			public <F extends Exception> R throwMappedFailure(@NotNull Function<E, F> exceptionTransformer) throws F {
				return value;
			}

			@NotNull
			@Override
			public R defaultOnFailure(@NotNull R defaultValue) {
				return value;
			}

			@Override
			public R nullOnFailure() {
				return value;
			}
		};
	}

	@Override
	public <S> FailureHandler<S, E> mapOnSuccess(Function<R, S> function) {
		return new FailureHandler<S, E>() {
			@Override
			public S handleFailure(@NotNull Function<E, S> exceptionHandler) {
				return function.apply(value);
			}

			@Override
			public S throwFailure() throws E {
				return function.apply(value);
			}

			@Override
			public <F extends Exception> S throwMappedFailure(@NotNull Function<E, F> exceptionTransformer) throws F {
				return function.apply(value);
			}

			@NotNull
			@Override
			public S defaultOnFailure(@NotNull S defaultValue) {
				return function.apply(value);
			}

			@Override
			public S nullOnFailure() {
				return function.apply(value);
			}
		};
	}
}
