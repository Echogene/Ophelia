package ophelia.function;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

/**
 * Like {@link BiConsumer} but can throw an exception.
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalBiConsumer<T, U, E extends Exception> {

	void accept(T t, U u) throws E;

	@NotNull
	default ExceptionalBiConsumer<T, U, E> andThen(@NotNull BiConsumer<? super T, ? super U> after) {
		return (t, u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}

	@NotNull
	default ExceptionalBiConsumer<T, U, E> andThen(@NotNull ExceptionalBiConsumer<? super T, ? super U, ? extends E> after) {
		return (t, u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}

	@NotNull
	default ExceptionalBiConsumer<T, U, E> andFinally(@NotNull BiConsumer<? super T, ? super U> after) {
		return (t, u) -> {
			try {
				accept(t, u);
			} finally {
				after.accept(t, u);
			}
		};
	}

	@NotNull
	default ExceptionalBiConsumer<T, U, E> andFinally(@NotNull ExceptionalBiConsumer<? super T, ? super U, ? extends E> after) {
		return (t, u) -> {
			try {
				accept(t, u);
			} finally {
				after.accept(t, u);
			}
		};
	}
}
