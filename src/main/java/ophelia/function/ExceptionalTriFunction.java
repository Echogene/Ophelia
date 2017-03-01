package ophelia.function;

import com.codepoetics.protonpack.functions.TriFunction;

/**
 * A functional interface that accepts three objects and returns one but can throw an exception.
 * @see TriFunction
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalTriFunction<A, B, C, R, E extends Exception> {

	R apply(A a, B b, C c) throws E;
}
