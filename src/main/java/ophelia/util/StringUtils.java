package ophelia.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static ophelia.util.NumberUtils.isOdd;

/**
 * @author Steven Weston
 */
public class StringUtils {

	/**
	 * Before the given string and after every new line character in the given string, insert the given character.
	 */
	@NotNull
	public static String addCharacterAfterEveryNewline(
			@NotNull String lines,
			char character
	) {
		return character + lines.replace("\n", "\n" + character);
	}

	/**
	 * Insert a tab character before the given string and every newline in it.
	 */
	@NotNull
	public static String indent(@NotNull String string) {
		return addCharacterAfterEveryNewline(string, '\t');
	}

	/**
	 * @param list a list of strings to concatenate
	 * @param joiner the string to insert between consecutive pairs of strings
	 * @return the concatenation of the list of strings with the given joiner between consecutive pairs
	 */
	@NotNull
	public static String join(
			@NotNull Collection<String> list,
			@NotNull String joiner
	) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String string : list) {
			if (!first) {
				sb.append(joiner);
			}
			sb.append(string);
			first = false;
		}
		return sb.toString();
	}

	/**
	 * @return the number of times the substring appears in the given string
	 */
	public static int count(
			@NotNull String substring,
			@NotNull String string
	) {
		return string.length() - string.replace(substring, "").length();
	}

	/**
	 * @return the substring of the given string starting and ending at the given indices.  If the end index is -1, then
	 *         it is treated as the index of the end of the string
	 */
	@NotNull
	public static String substring(@NotNull String string, int startIndex, int endIndex) {
		if (endIndex == -1) {
			return string.substring(startIndex);
		} else {
			return string.substring(startIndex, endIndex);
		}
	}

	/**
	 * Print the concatenation of the strings obtained from applying the given presentation function to the given
	 * collection.  Then print another line of the same length highlighting which elements match the given predicate.
	 */
	@NotNull
	public static <T> String identify(
			@NotNull Collection<T> collection,
			@NotNull Predicate<T> predicate,
			@NotNull Function<T, String> presentationFunction
	) {

		Map<T, String> presentations = new HashMap<>();
		for (T t : collection) {
			presentations.put(t, presentationFunction.apply(t));
		}
		StringBuilder sb = new StringBuilder();
		for (T t : collection) {
			sb.append(presentations.get(t));
		}
		sb.append("\n");
		for (T t : collection) {
			int length = presentations.get(t).length();
			if (length == 0) {
				continue; // Even if the element matches, we can't really point it out.
			}
			if (predicate.test(t)) {
				if (length == 1) {
					sb.append("│");
				} else if (length == 2) {
					sb.append("├╯");
				} else if (isOdd(length)) {
					sb.append("╰");
					repeatedlyAppend(length / 2 - 1, sb, "─");
					sb.append("┬");
					repeatedlyAppend(length / 2 - 1, sb, "─");
					sb.append("╯");
				} else {
					sb.append("╰");
					repeatedlyAppend(length / 2 - 2, sb, "─");
					sb.append("┬");
					repeatedlyAppend(length / 2 - 1, sb, "─");
					sb.append("╯");
				}
			} else {
				repeatedlyAppend(length, sb, " ");
			}
		}
		return sb.toString();
	}

	public static void repeatedlyAppend(int times, StringBuilder sb, String toAppend) {
		for (int i = 0; i < times; i++) {
			sb.append(toAppend);
		}
	}

	/**
	 * @return the string "null" if the input is null, otherwise the result of its toString
	 */
	@NotNull
	public static String safeToString(@Nullable Object o) {
		if (o == null) {
			return "null";
		} else {
			return o.toString();
		}
	}

	/**
	 * Replaces the last occurrence of the second given string in the first given string with the third.
	 * @param s the string in which we want to replace
	 * @param toReplace the string to replace
	 * @param substitution the string with which to replace
	 * @return the first string after substitution
	 */
	@NotNull
	public static String replaceLast(@NotNull String s, String toReplace, String substitution) {
		StringBuilder sb = new StringBuilder(s);
		int index = s.lastIndexOf(toReplace);
		sb.replace(index, index + toReplace.length(), substitution);
		return sb.toString();
	}

	/**
	 * @param string the string whose first character should be capitalised
	 * @return the given string but ensuring that its first character is majuscule
	 */
	@NotNull
	public static String uppercaseFirst(@NotNull String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
}
