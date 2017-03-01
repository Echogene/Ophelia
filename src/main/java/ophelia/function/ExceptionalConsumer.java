package ophelia.function;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Like {@link Consumer} but can throw an exception.
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalConsumer<T, E extends Exception> {

	void accept(T t) throws E;

	@NotNull
	default ExceptionalConsumer<T, E> andThen(@NotNull Consumer<? super T> after) {
		return t -> {
			accept(t);
			after.accept(t);
		};
	}

	@NotNull
	default ExceptionalConsumer<T, E> andThen(@NotNull ExceptionalConsumer<? super T, ? extends E> after) {
		return t -> {
			accept(t);
			after.accept(t);
		};
	}

	@NotNull
	default ExceptionalConsumer<T, E> andFinally(@NotNull Consumer<? super T> after) {
		return t -> {
			try {
				accept(t);
			} finally {
				after.accept(t);
			}
		};
	}

	@NotNull
	default ExceptionalConsumer<T, E> andFinally(@NotNull ExceptionalConsumer<? super T, ? extends E> after) {
		return t -> {
			try {
				accept(t);
			} finally {
				after.accept(t);
			}
		};
	}
}
