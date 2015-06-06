package ophelia.function;

/**
 * Like a {@link java.util.function.BiFunction}, but can throw an exception.
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalBiFunction<D, F, R, E extends Exception> {

	R apply(D d, F f) throws E;
}
