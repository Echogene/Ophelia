package ophelia.exceptions.maybe;

import ophelia.util.function.ExceptionalSupplier;
import org.jetbrains.annotations.NotNull;

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

	@Override
	public VoidFailureHandler consumeOnSuccess(Consumer<R> consumer) {
		return new VoidFailureHandler() {
			@Override
			public void consumeOnFailure(Consumer<StackedException> exceptionHandler) {
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

	@Override
	public FailureHandler<R> returnOnSuccess() {
		return new DefaultFailureHandler<>(exception);
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

		@NotNull
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
