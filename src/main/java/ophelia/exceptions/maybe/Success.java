package ophelia.exceptions.maybe;

import ophelia.function.ExceptionalSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
class Success<R> implements Maybe<R> {

	private final R value;

	Success(R r) {
		this.value = r;
	}

	@NotNull
	@Override
	public VoidFailureHandler consumeOnSuccess(@NotNull Consumer<R> consumer) {
		return new VoidFailureHandler() {
			@Override
			public void consumeOnFailure(Consumer<StackedException> exceptionHandler) {
				consumer.accept(value);
			}

			@Override
			public void throwOnFailure() throws StackedException {
				consumer.accept(value);
			}

			@Override
			public void ignoreOnFailure() {
				consumer.accept(value);
			}
		};
	}

	@NotNull
	@Override
	public FailureHandler<R> returnOnSuccess() {
		return new DefaultFailureHandler<>(value);
	}

	static class DefaultFailureHandler<T> implements FailureHandler<T> {
		private final T t;

		DefaultFailureHandler(T t) {
			this.t = t;
		}

		@Override
		public T resolveFailure(@NotNull Function exceptionHandler) {
			return t;
		}

		@Override
		public T throwAllFailures() throws StackedException {
			return t;
		}

		@NotNull
		@Override
		public T defaultOnFailure(@NotNull Object defaultValue) {
			return t;
		}

		@Nullable
		@Override
		public T nullOnFailure() {
			return t;
		}

		@NotNull
		@Override
		public FailureHandler<T> tryAgain(@NotNull ExceptionalSupplier<T, Exception> supplier) {
			return this;
		}

		@Override
		public <F extends Exception> T throwMappedFailure(
				@NotNull Function<StackedException, F> exceptionTransformer
		) throws F {

			return t;
		}
	}
}
