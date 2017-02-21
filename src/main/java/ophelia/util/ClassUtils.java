package ophelia.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static ophelia.util.function.FunctionUtils.safeGet;

/**
 * @author Steven Weston
 */
public class ClassUtils {

	/**
	 * @param clazz the class we want the simple name of
	 * @return the simple name of the class if it is not null, or null otherwise.
	 */
	@Contract("null -> null")
	public static String safeSimpleName(@Nullable Class clazz) {
		return safeGet(clazz, Class::getSimpleName);
	}

	@NotNull
	public static String getSimpleName(@NotNull String canonicalClassName) {
		return canonicalClassName.substring(canonicalClassName.lastIndexOf(".") + 1);
	}
}
