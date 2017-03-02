package ophelia.function;

import com.codepoetics.protonpack.functions.TriFunction;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * A functional interface that accepts three objects and returns one but can throw an exception.
 * @see TriFunction
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalTriFunction<A, B, C, R, E extends Exception> {

	R apply(A a, B b, C c) throws E;

	@NotNull
	default <V> ExceptionalTriFunction<A, B, C, V, E> andThen(@NotNull Function<? super R, ? extends V> after) {
		return (a, b, c) -> after.apply(apply(a, b, c));
	}

	@NotNull
	default <V> ExceptionalTriFunction<A, B, C, V, E> andThen(@NotNull ExceptionalFunction<? super R, ? extends V, ? extends E> after) {
		return (a, b, c) -> after.apply(apply(a, b, c));
	}
}
