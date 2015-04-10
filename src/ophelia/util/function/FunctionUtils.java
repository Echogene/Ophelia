package ophelia.util.function;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Steven Weston
 */
public class FunctionUtils {

	@Contract("null,_ -> null")
	public static <S, T> T safeGet(@Nullable S s, @NotNull Function<S, T> getter) {
		if (s == null) {
			return null;
		} else {
			return getter.apply(s);
		}
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

	@Nullable
	public static <S, T> T ignoreExceptions(@Nullable S s, @NotNull Function<S, T> function) {
		try {
			return function.apply(s);
		} catch (Exception e) {
			return null;
		}
	}
}
