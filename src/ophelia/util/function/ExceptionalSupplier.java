package ophelia.util.function;

/**
 * Like a {@link java.util.function.Supplier} but can throw an Exception.
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalSupplier<T, E extends Exception> {

    T get() throws E;
}
