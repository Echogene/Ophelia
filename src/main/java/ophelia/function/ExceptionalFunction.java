package ophelia.function;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Like a {@link java.util.function.Function}, but can throw an exception.
 * @author Steven Weston
 */
public interface ExceptionalFunction<D, R, E extends Exception> {

	R apply(D t) throws E;

	@NotNull
	default <V> ExceptionalFunction<V, R, E> compose(@NotNull Function<? super V, ? extends D> before) {
		return v -> apply(before.apply(v));
	}

	@NotNull
	default <V> ExceptionalFunction<D, V, E> andThen(@NotNull Function<? super R, ? extends V> after) {
		return d -> after.apply(apply(d));
	}

	@NotNull
	default <V> ExceptionalFunction<V, R, E> compose(@NotNull ExceptionalFunction<? super V, ? extends D, E> before) {
		return v -> apply(before.apply(v));
	}

	@NotNull
	default <V> ExceptionalFunction<D, V, E> andThen(@NotNull ExceptionalFunction<? super R, ? extends V, E> after) {
		return d -> after.apply(apply(d));
	}

	/**
	 * Compose two functions that throw different exceptions and return a function that throws the union type of those
	 * exceptions.
	 */
	@NotNull
	static <
			R, S, T,
			E extends Exception,
			F extends E,
			G extends E
	>
	ExceptionalFunction<R, T, E> compose(
			@NotNull ExceptionalFunction<? super R, ? extends S, F> before,
			@NotNull ExceptionalFunction<? super S, ? extends T, G> after
	) {
		return t -> after.apply(before.apply(t));
	}
}
