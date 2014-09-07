package util;

import static util.function.FunctionUtils.safeGet;

/**
 * @author Steven Weston
 */
public class ClassUtils {

	public static String safeSimpleName(Class clazz) {
		return safeGet(clazz, Class::getSimpleName);
	}
}
