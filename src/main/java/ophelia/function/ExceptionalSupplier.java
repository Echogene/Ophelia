package ophelia.function;

import java.util.function.Supplier;

/**
 * Like a {@link Supplier} but can throw an Exception.
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalSupplier<T, E extends Exception> {

    T get() throws E;
}
