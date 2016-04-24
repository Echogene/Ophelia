package ophelia.exceptions.voidmaybe;

import ophelia.exceptions.CollectedException;
import ophelia.function.ExceptionalBiConsumer;
import ophelia.function.ExceptionalConsumer;
import ophelia.function.ExceptionalRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static ophelia.util.FunctionUtils.not;

/**
 * @author Steven Weston
 */
public interface VoidMaybe extends VoidMaybeHandler {

	/**
	 * Convert an exceptional runnable into a supplier of maybes.
	 */
	@NotNull
	static Supplier<VoidMaybe> wrapOutput(
			@NotNull ExceptionalRunnable<? extends Exception> runnable
	) {
		return () -> {
			try {
				runnable.run();
				return new Success();
			} catch (Exception e) {
				return new Failure(e);
			}
		};
	}

	/**
	 * Convert an exceptional consumer into a function of maybes.
	 */
	@NotNull
	static <T> Function<T, VoidMaybe> wrapOutput(
			@NotNull ExceptionalConsumer<T, ? extends Exception> consumer
	) {
		return t -> {
			try {
				consumer.accept(t);
				return new Success();
			} catch (Exception e) {
				return new Failure(e);
			}
		};
	}

	/**
	 * Convert an exceptional biconsumer into a bifunction of maybes.
	 */
	@NotNull
	static <T, U> BiFunction<T, U, VoidMaybe> wrapOutput(
			@NotNull ExceptionalBiConsumer<T, U, ? extends Exception> consumer
	) {
		return (t, u) -> {
			try {
				consumer.accept(t, u);
				return new Success();
			} catch (Exception e) {
				return new Failure(e);
			}
		};
	}

	static VoidMaybe mergeFailures(Collection<VoidMaybe> maybes) {
		//noinspection ThrowableResultOfMethodCallIgnored
		return maybes.stream()
				.filter(not(VoidMaybe::isSuccess))
				.map(VoidMaybe::getException)
				.collect(Collector.<Exception, List<Exception>, VoidMaybe>of(
						ArrayList::new,
						List::add,
						(left, right) -> { left.addAll(right); return left; },
						a -> {
							if (a.isEmpty()) {
								return new Success();
							} else {
								return new Failure(new CollectedException(a));
							}
						}
				));
	}
}
