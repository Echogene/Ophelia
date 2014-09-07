package util.function;

import java.util.function.Function;

/**
 * @author Steven Weston
 */
public class FunctionUtils {

	public static <S, T> T safeGet(S s, Function<S, T> getter) {
		if (s == null) {
			return null;
		} else {
			return getter.apply(s);
		}
	}
}
