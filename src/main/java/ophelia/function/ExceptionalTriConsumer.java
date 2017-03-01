package ophelia.function;

/**
 * A functional interface that accepts three objects and can throw an exception.
 * @see TriConsumer
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalTriConsumer<T, U, V, E extends Exception> {

	void accept(T t, U u, V v) throws E;
}
