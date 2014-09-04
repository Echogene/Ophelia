package util;

import java.util.List;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
public class TreeUtils {

	public static <T> String prettyPrint(
			List<T> list,
			Function<T, String> presentationFunction,
			Function<T, List<T>> childrenFunction,
			int indentation
	) {
		StringBuilder output = new StringBuilder();
		for (T parent : list) {
			output.append(presentationFunction.apply(parent));
			output.append("\n");
			List<T> children = childrenFunction.apply(parent);
			if (children == null || children.isEmpty()) {
				continue;
			}
			String childString = prettyPrint(children, presentationFunction, childrenFunction, indentation);
			String[] split = childString.split("\n");
			for (int i = 0; i < split.length; i++) {
				String s = split[i];
				String trimmed = s.trim();
				Boolean isAnotherChild = false;
				for (int j = i + 1; j < split.length; j++) {
					String t = split[j].trim();
					if (!isPartOfTree(t)) {
						isAnotherChild = true;
					}
				}
				if (isPartOfTree(trimmed)) {
					if (isAnotherChild) {
						output.append("│");
					} else {
						output.append(" ");
					}
					StringUtils.repeatedlyAppend(indentation, output, " ");
				} else {
					if (isAnotherChild) {
						output.append("├");
					} else {
						output.append("└");
					}
					StringUtils.repeatedlyAppend(indentation, output, "─");
				}
				output.append(s);
				output.append("\n");
			}
		}
		return output.toString();
	}

	// Not sure what to call this...
	private static boolean isPartOfTree(String trimmed) {
		return trimmed.startsWith("└") || trimmed.startsWith("├") || trimmed.startsWith("│");
	}
}
