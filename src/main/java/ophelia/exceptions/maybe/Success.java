package ophelia.exceptions.maybe;

import ophelia.exceptions.StackedException;
import ophelia.function.ExceptionalFunction;
import ophelia.function.ExceptionalSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
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
			public <F extends Exception> void throwMappedFailure(@NotNull Function<StackedException, F> exceptionTransformer) throws F {
				consumer.accept(value);
			}

			@Override
			public void consumeOnFailure(@NotNull Consumer<StackedException> exceptionHandler) {
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

	@NotNull
	@Override
	public <S> FailureHandler<S> mapOnSuccess(@NotNull Function<R, S> map) {
		return new DefaultFailureHandler<>(map.apply(value));
	}

	@Override
	public boolean isSuccess() {
		return true;
	}

	@NotNull
	@Override
	public Optional<StackedException> getException() {
		return Optional.empty();
	}

	@NotNull
	@Override
	public <S> Maybe<S> map(@NotNull ExceptionalFunction<R, S, ?> map) {
		try {
			return new Success<>(map.apply(value));
		} catch (Exception e) {
			return new Failure<>(e);
		}
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

		@Nullable
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Success<?> success = (Success<?>) o;

		return value != null ? value.equals(success.value) : success.value == null;
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
