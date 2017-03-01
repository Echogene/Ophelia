package ophelia.util.function;

import com.codepoetics.protonpack.functions.TriFunction;
import ophelia.function.*;
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

	@NotNull
	public <R, S, T> Function<R, Function<S, T>> curry(@NotNull BiFunction<R, S, T> function) {
		return r -> s -> function.apply(r, s);
	}

	@NotNull
	public <R, S> Function<R, Consumer<S>> curry(@NotNull BiConsumer<R, S> consumer) {
		return r -> s -> consumer.accept(r, s);
	}

	@NotNull
	public <Q, R, S, T> Function<Q, Function<R, Function<S, T>>> curry(@NotNull TriFunction<Q, R, S, T> function) {
		return q -> r -> s -> function.apply(q, r, s);
	}

	@NotNull
	public <Q, R, S> Function<Q, Function<R, Consumer<S>>> curry(@NotNull TriConsumer<Q, R, S> consumer) {
		return q -> r -> s -> consumer.accept(q, r, s);
	}

	@NotNull
	public <R, S, T, E extends Exception> Function<R, ExceptionalFunction<S, T, E>> curry(@NotNull ExceptionalBiFunction<R, S, T, E> function) {
		return r -> s -> function.apply(r, s);
	}

	@NotNull
	public <R, S, E extends Exception> Function<R, ExceptionalConsumer<S, E>> curry(@NotNull ExceptionalBiConsumer<R, S, E> consumer) {
		return r -> s -> consumer.accept(r, s);
	}

	@NotNull
	public <Q, R, S, T, E extends Exception> Function<Q, Function<R, ExceptionalFunction<S, T, E>>> curry(@NotNull ExceptionalTriFunction<Q, R, S, T, E> function) {
		return q -> r -> s -> function.apply(q, r, s);
	}

	@NotNull
	public <Q, R, S, E extends Exception> Function<Q, Function<R, ExceptionalConsumer<S, E>>> curry(@NotNull ExceptionalTriConsumer<Q, R, S, E> consumer) {
		return q -> r -> s -> consumer.accept(q, r, s);
	}
}
