package ophelia.util.function;

import ophelia.function.ExceptionalSupplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * @author Steven Weston
 */
public class FunctionUtils {

	/**
	 * Safely get something from a potentially null object, returning null if that object is null.
	 */
	@Contract("null,_ -> null")
	public static <S, T> T safeGet(@Nullable S s, @NotNull Function<S, T> getter) {
		if (s == null) {
			return null;
		} else {
			return getter.apply(s);
		}
	}

	@NotNull
	public static <S, T> Function<S, T> safeWrap(Function<S, T> getter) {
		return s -> s == null ? null : getter.apply(s);
	}

	@NotNull
	public static <T> UnaryOperator<T> safeWrap(UnaryOperator<T> getter) {
		return s -> s == null ? null : getter.apply(s);
	}

	/**
	 * @return the image (as a list) of the function on the given array.
	 */
	@NotNull
	public static <S, T> List<T> image(@NotNull S[] list, @NotNull Function<? super S, T> function) {
		return image(Arrays.asList(list), function);
	}

	/**
	 * @return the image of the function on the given list.
	 */
	@NotNull
	public static <S, T> List<T> image(@NotNull List<S> list, @NotNull Function<? super S, T> function) {
		return list.stream()
				.map(function)
				.collect(Collectors.toList());
	}

	/**
	 * @return the image of the function on the given set.
	 */
	@NotNull
	public static <S, T> Set<T> image(@NotNull Set<S> set, @NotNull Function<? super S, T> function) {
		return set.stream()
				.map(function)
				.collect(Collectors.toSet());
	}

	@Nullable
	public static <S, T> T ignoreExceptions(@Nullable S s, @NotNull Function<S, T> function) {
		try {
			return function.apply(s);
		} catch (Exception e) {
			return null;
		}
	}

	@Nullable
	public static <T, E extends Exception> T ignoreExceptions(@NotNull ExceptionalSupplier<T, E> supplier) {
		try {
			return supplier.get();
		} catch (Exception e) {
			return null;
		}
	}

	@NotNull
	public static <T> Consumer<T> checkThenMaybeConsume(@NotNull Predicate<T> predicate, @NotNull Consumer<T> consumer) {
		return t -> {
			if (predicate.test(t)) {
				consumer.accept(t);
			}
		};
	}

	@NotNull
	public static <S, T> BiConsumer<S, T> checkThenMaybeConsume(@NotNull BiPredicate<S, T> predicate, @NotNull BiConsumer<S, T> consumer) {
		return (s, t) -> {
			if (predicate.test(s, t)) {
				consumer.accept(s, t);
			}
		};
	}

	@NotNull
	public static <S, T> BiConsumer<S, T> checkFirstThenMaybeConsume(@NotNull Predicate<S> predicate, @NotNull BiConsumer<S, T> consumer) {
		return (s, t) -> {
			if (predicate.test(s)) {
				consumer.accept(s, t);
			}
		};
	}

	@NotNull
	public static <S, T> BiConsumer<S, T> checkSecondThenMaybeConsume(@NotNull Predicate<T> predicate, @NotNull BiConsumer<S, T> consumer) {
		return (s, t) -> {
			if (predicate.test(t)) {
				consumer.accept(s, t);
			}
		};
	}

	@NotNull
	public static <R, S, T> BiConsumer<R, T> mapFirstThenConsume(@NotNull Function<R, S> function, @NotNull BiConsumer<S, T> consumer) {
		return (r, t) -> consumer.accept(function.apply(r), t);
	}

	@NotNull
	public static <R, S, T> BiConsumer<S, R> mapSecondThenConsume(@NotNull Function<R, T> function, @NotNull BiConsumer<S, T> consumer) {
		return (s, r) -> consumer.accept(s, function.apply(r));
	}
}
