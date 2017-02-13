package ophelia.exceptions.voidmaybe;

import ophelia.exceptions.CollectedException;
import ophelia.function.ExceptionalBiConsumer;
import ophelia.function.ExceptionalConsumer;
import ophelia.function.ExceptionalRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static ophelia.exceptions.voidmaybe.Success.SUCCESS;

/**
 * @author Steven Weston
 */
public interface VoidMaybe extends VoidMaybeHandler {

	/**
	 * Convert an exceptional runnable into a supplier of maybes.
	 */
	@NotNull
	static Supplier<VoidMaybe> wrap(
			@NotNull ExceptionalRunnable<? extends Exception> runnable
	) {
		return () -> {
			try {
				runnable.run();
				return SUCCESS;
			} catch (Exception e) {
				return new Failure(e);
			}
		};
	}

	/**
	 * Convert an exceptional consumer into a function of maybes.
	 */
	@NotNull
	static <T> Function<T, VoidMaybe> wrap(
			@NotNull ExceptionalConsumer<T, ? extends Exception> consumer
	) {
		return t -> {
			try {
				consumer.accept(t);
				return SUCCESS;
			} catch (Exception e) {
				return new Failure(e);
			}
		};
	}

	/**
	 * Convert an exceptional biconsumer into a bifunction of maybes.
	 */
	@NotNull
	static <T, U> BiFunction<T, U, VoidMaybe> wrap(
			@NotNull ExceptionalBiConsumer<T, U, ? extends Exception> consumer
	) {
		return (t, u) -> {
			try {
				consumer.accept(t, u);
				return SUCCESS;
			} catch (Exception e) {
				return new Failure(e);
			}
		};
	}

	/**
	 * Create maybe that is a success.
	 */
	@NotNull
	@Contract(pure = true)
	static VoidMaybe success() {
		return SUCCESS;
	}

	/**
	 * Create maybe that is unsuccessful.
	 */
	@NotNull
	static VoidMaybe failure(@NotNull Exception e) {
		return new Failure(e);
	}

	/**
	 * Create a maybe that wraps multiple failures.
	 */
	@NotNull
	static VoidMaybe multipleFailures(@NotNull List<Exception> exceptions) {
		return failure(new CollectedException(exceptions));
	}

	static VoidMaybe mergeFailures(Collection<VoidMaybe> maybes) {
		//noinspection ThrowableResultOfMethodCallIgnored
		return maybes.stream()
				.map(VoidMaybe::getException)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collector.<Exception, List<Exception>, VoidMaybe>of(
						ArrayList::new,
						List::add,
						(left, right) -> { left.addAll(right); return left; },
						exceptions -> {
							if (exceptions.isEmpty()) {
								return SUCCESS;

							} else if (exceptions.size() == 1) {
								return new Failure(exceptions.get(0));

							} else {
								return new Failure(new CollectedException(exceptions));
							}
						}
				));
	}
}
