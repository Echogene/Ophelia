package ophelia.util.function;

/**
 * Like {@link java.util.function.Consumer} but can throw an exception.
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalConsumer<T, E extends Exception> {

	void accept(T t) throws E;
}
