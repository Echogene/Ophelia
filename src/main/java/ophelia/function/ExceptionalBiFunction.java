package ophelia.function;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Like a {@link BiFunction}, but can throw an exception.
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalBiFunction<T, U, R, E extends Exception> {

	R apply(T t, U u) throws E;

	@NotNull
	default <V> ExceptionalBiFunction<T, U, V, E> andThen(@NotNull Function<? super R, ? extends V> after) {
		return (t, u) -> after.apply(apply(t, u));
	}

	@NotNull
	default <V> ExceptionalBiFunction<T, U, V, E> andThen(@NotNull ExceptionalFunction<? super R, ? extends V, ? extends E> after) {
		return (t, u) -> after.apply(apply(t, u));
	}
}
