package ophelia.function;

/**
 * Like a {@link java.util.function.Function}, but can throw an exception.
 * @author Steven Weston
 */
public interface ExceptionalFunction<D, R, E extends Exception> {

	R apply(D t) throws E;
}
