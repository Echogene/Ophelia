package ophelia.exceptions.maybe;

import ophelia.exceptions.StackedException;
import ophelia.function.ExceptionalSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
class Failure<R> implements Maybe<R> {

	private final StackedException exception;

	Failure(Exception e) {
		this.exception = new StackedException(e);
	}

	@NotNull
	@Override
	public VoidFailureHandler consumeOnSuccess(@NotNull Consumer<R> consumer) {
		return new VoidFailureHandler() {
			@Override
			public <F extends Exception> void throwMappedFailure(@NotNull Function<StackedException, F> exceptionTransformer) throws F {
				throw exceptionTransformer.apply(exception);
			}

			@Nullable
			@Override
			public StackedException getException() {
				return exception;
			}

			@Override
			public void consumeOnFailure(@NotNull Consumer<StackedException> exceptionHandler) {
				exceptionHandler.accept(exception);
			}

			@Override
			public void throwOnFailure() throws StackedException {
				throw exception;
			}

			@Override
			public void ignoreOnFailure() {}
		};
	}

	@NotNull
	@Override
	public FailureHandler<R> returnOnSuccess() {
		return new DefaultFailureHandler<>(exception);
	}

	@NotNull
	@Override
	public <S> FailureHandler<S> mapOnSuccess(Function<R, S> map) {
		return new DefaultFailureHandler<>(exception);
	}

	@Override
	public boolean isSuccess() {
		return false;
	}

	static class DefaultFailureHandler<S> implements FailureHandler<S> {

		private final StackedException exception;

		DefaultFailureHandler(StackedException exception) {
			this.exception = exception;
		}

		@Override
		public S resolveFailure(@NotNull Function<StackedException, S> exceptionHandler) {
			return exceptionHandler.apply(exception);
		}

		@Override
		public S throwAllFailures() throws StackedException {
			throw exception;
		}

		@Override
		public <F extends Exception> S throwMappedFailure(
				@NotNull Function<StackedException, F> exceptionTransformer
		) throws F {

			throw exceptionTransformer.apply(exception);
		}

		@Nullable
		@Override
		public S defaultOnFailure(@NotNull S defaultValue) {
			return defaultValue;
		}

		@Override
		public S nullOnFailure() {
			return null;
		}

		@NotNull
		@Override
		public FailureHandler<S> tryAgain(@NotNull ExceptionalSupplier<S, Exception> supplier) {
			try {
				return new Success.DefaultFailureHandler<>(supplier.get());
			} catch (Exception e) {
				exception.addException(e);
				return this;
			}
		}
	}
}
