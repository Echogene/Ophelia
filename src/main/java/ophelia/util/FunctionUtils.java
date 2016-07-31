package ophelia.util;

import ophelia.function.ExceptionalSupplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * @author Steven Weston
 */
public class FunctionUtils {

	private static final Predicate<?> NOT_NULL = o -> o != null;
	private static final Predicate<?> NULL = o -> o == null;

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
	public static <T> Predicate<T> not(@NotNull Predicate<? super T> predicate) {
		return t -> !predicate.test(t);
	}

	@NotNull
	public static <T> Predicate<T> notNull() {
		//noinspection unchecked
		return (Predicate<T>) NOT_NULL;
	}

	@NotNull
	public static <T> Predicate<T> isNull() {
		//noinspection unchecked
		return (Predicate<T>) NULL;
	}
}
