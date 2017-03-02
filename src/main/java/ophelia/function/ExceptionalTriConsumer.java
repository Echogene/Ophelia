package ophelia.function;

import org.jetbrains.annotations.NotNull;

/**
 * A functional interface that accepts three objects and can throw an exception.
 * @see TriConsumer
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalTriConsumer<T, U, V, E extends Exception> {

	void accept(T t, U u, V v) throws E;

	@NotNull
	default ExceptionalTriConsumer<T, U, V, E> andThen(@NotNull TriConsumer<? super T, ? super U, ? super V> after) {
		return (t, u, v) -> {
			accept(t, u, v);
			after.accept(t, u, v);
		};
	}

	@NotNull
	default ExceptionalTriConsumer<T, U, V, E> andThen(@NotNull ExceptionalTriConsumer<? super T, ? super U, ? super V, ? extends E> after) {
		return (t, u, v) -> {
			accept(t, u, v);
			after.accept(t, u, v);
		};
	}

	@NotNull
	default ExceptionalTriConsumer<T, U, V, E> andFinally(@NotNull TriConsumer<? super T, ? super U, ? super V> after) {
		return (t, u, v) -> {
			try {
				accept(t, u, v);
			} finally {
				after.accept(t, u, v);
			}
		};
	}

	@NotNull
	default ExceptionalTriConsumer<T, U, V, E> andFinally(@NotNull ExceptionalTriConsumer<? super T, ? super U, ? super V, ? extends E> after) {
		return (t, u, v) -> {
			try {
				accept(t, u, v);
			} finally {
				after.accept(t, u, v);
			}
		};
	}
}
