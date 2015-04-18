package ophelia.exceptions.maybe;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
class Failure<R, E extends Exception> implements Maybe<R, E> {

	private final E exception;

	Failure(E e) {
		this.exception = e;
	}

	@Override
	public VoidFailureHandler<E> consumeOnSuccess(Consumer<R> consumer) {
		return new VoidFailureHandler<E>() {
			@Override
			public void consumeOnFailure(Consumer<E> exceptionHandler) {
				exceptionHandler.accept(exception);
			}

			@Override
			public void throwOnFailure() throws E {
				throw exception;
			}

			@Override
			public void ignoreOnFailure() {}
		};
	}

	@Override
	public FailureHandler<R, E> returnOnSuccess() {
		return new DefaultFailureHandler<>();
	}

	@Override
	public <S> FailureHandler<S, E> mapOnSuccess(Function<R, S> function) {
		return new DefaultFailureHandler<>();
	}

	private class DefaultFailureHandler<S> implements FailureHandler<S, E> {
		@Override
		public S handleFailure(@NotNull Function<E, S> exceptionHandler) {
			return exceptionHandler.apply(exception);
		}

		@Override
		public S throwOnFailure() throws E {
			throw exception;
		}

		@NotNull
		@Override
		public S defaultOnFailure(@NotNull S defaultValue) {
			return defaultValue;
		}

		@Override
		public S nullOnFailure() {
			return null;
		}
	}
}
