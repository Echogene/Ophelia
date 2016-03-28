package ophelia.exceptions.voidmaybe;

import ophelia.exceptions.CollectedException;
import ophelia.function.ExceptionalBiConsumer;
import ophelia.function.ExceptionalConsumer;
import ophelia.function.ExceptionalRunnable;
import ophelia.util.StreamUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

	static void throwIfSuccessNotUnique(Collection<VoidMaybe> maybes) throws CollectedException {
		//noinspection ThrowableResultOfMethodCallIgnored
		maybes.stream()
				.filter(VoidMaybeHandler::isSuccess)
				.collect(StreamUtils.findUnique())
				.ignoreOnSuccess()
				.throwMappedFailure(e -> new CollectedException(
						maybes.stream()
								.map(VoidMaybeHandler::getException)
								.collect(Collectors.toList())
				));
	}

	static VoidMaybe failIfSuccessNotUnique(Collection<VoidMaybe> maybes) {
		//noinspection ThrowableResultOfMethodCallIgnored
		return maybes.stream()
				.filter(VoidMaybeHandler::isSuccess)
				.collect(StreamUtils.findUnique())
				.mapOnSuccess(uniqueMaybe -> (VoidMaybe) new Success())
				.resolveFailure(e -> new Failure(new CollectedException(
						maybes.stream()
								.map(VoidMaybeHandler::getException)
								.collect(Collectors.toList())
				)));
	}
}
