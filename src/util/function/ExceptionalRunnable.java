package util.function;

/**
 * Like a {@link Runnable} but can throw an Exception
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalRunnable<E extends Exception> {

	void run() throws E;
}
