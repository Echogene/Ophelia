package ophelia.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static ophelia.util.FunctionUtils.safeGet;

/**
 * @author Steven Weston
 */
public class ClassUtils {

	@Contract("null -> null")
	public static String safeSimpleName(@Nullable Class clazz) {
		return safeGet(clazz, Class::getSimpleName);
	}

	@NotNull
	public static String getSimpleName(@NotNull String canonicalClassName) {
		return canonicalClassName.substring(canonicalClassName.lastIndexOf("."));
	}
}
