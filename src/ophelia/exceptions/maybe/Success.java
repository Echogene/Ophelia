package ophelia.exceptions.maybe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
		return new DefaultFailureHandler<>(value);
	}

	@Override
	public <S> FailureHandler<S, E> mapOnSuccess(Function<R, S> function) {
		return new DefaultFailureHandler<>(function.apply(value));
	}

	private class DefaultFailureHandler<T> implements FailureHandler<T, E> {
		private final T t;

		private DefaultFailureHandler(T t) {
			this.t = t;
		}

		@Override
		public T handleFailure(@NotNull Function exceptionHandler) {
			return t;
		}

		@Override
		public T throwFailure() throws E {
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

		@Override
		public <F extends Exception> T throwMappedFailure(@NotNull Function<E, F> exceptionTransformer) throws F {
			return t;
		}
	}
}
