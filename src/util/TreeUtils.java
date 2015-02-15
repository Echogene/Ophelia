package util;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
public class TreeUtils {

	/**
	 * @param list the list of root nodes of the tree
	 * @param presentationFunction how to represent each node
	 * @param childrenFunction the function to recurse down
	 * @param indentation the number of spaces by which to indent the child nodes
	 * @param <T> the type of nodes in the tree
	 * @return a string to represent the tree visually in monospaced fonts
	 */
	@NotNull
	public static <T> String prettyPrint(
			@NotNull List<T> list,
			@NotNull Function<T, String> presentationFunction,
			@NotNull Function<T, List<T>> childrenFunction,
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
	private static boolean isPartOfTree(@NotNull String trimmed) {
		return trimmed.startsWith("└") || trimmed.startsWith("├") || trimmed.startsWith("│");
	}
}
