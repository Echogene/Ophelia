package ophelia.function;

import org.jetbrains.annotations.NotNull;

/**
 * Like a {@link Runnable} but can throw an Exception
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalRunnable<E extends Exception> {

	void run() throws E;

	/**
	 * Try to run this runnable and always run the given runnable whether or not this one fails.
	 */
	@NotNull
	default ExceptionalRunnable<E> andFinally(@NotNull Runnable after) {
		return () -> {
			try {
				run();
			} finally {
				after.run();
			}
		};
	}

	/**
	 * Try to run this runnable and always try to run the given runnable whether or not this one fails.
	 */
	@NotNull
	default ExceptionalRunnable<E> andFinally(@NotNull ExceptionalRunnable<E> after) {
		return () -> {
			try {
				run();
			} finally {
				after.run();
			}
		};
	}
}
