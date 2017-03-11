package ophelia.util.hierarchy;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class HierarchyParser<T, C extends Collection<T>> {

	@NotNull
	private final Indentation indentation;

	@NotNull
	private final Function<String, T> nodeConstructor;

	@NotNull
	private final BiConsumer<T, C> childrenSetter;

	@NotNull
	private final Supplier<C> childrenConstructor;

	private HierarchyParser(
			@NotNull Indentation indentation,
			@NotNull Function<String, T> nodeConstructor,
			@NotNull BiConsumer<T, C> childrenSetter,
			@NotNull Supplier<C> childrenConstructor
	) {
		this.indentation = indentation;
		this.nodeConstructor = nodeConstructor;
		this.childrenSetter = (t, c) -> {
			if (t != null) {
				childrenSetter.accept(t, c);
			}
		};
		this.childrenConstructor = childrenConstructor;
	}

	@NotNull
	public static <T> HierarchyParser<T, List<T>> forChildList(
			@NotNull Function<String, T> nodeConstructor,
			@NotNull BiConsumer<T, List<T>> childrenSetter
	) {
		return new HierarchyParser<>(
				Indentation.ofString(" "),
				nodeConstructor,
				childrenSetter,
				ArrayList::new
		);
	}

	/**
	 * Given a string representation of a hierarchy, return a list of its root nodes, which should be populated with
	 * children.
	 * @param rows the rows that represent a hierarchy where children are indented using the given {@link #indentation}
	 * @return the list of root nodes
	 */
	@NotNull
	public C parse(@NotNull String... rows) {
		HierarchyState state = new HierarchyState(rows);
		return state.process();
	}

	private class HierarchyState {
		@Nullable
		T lastNode = globalNode();

		@NotNull
		final Map<T, C> parentToChildren = new HashMap<T, C>() {{
			// Make sure the global node has a an empty list of children, rather than a null one.
			put(globalNode(), childrenConstructor.get());
		}};

		int lastIndentation = -1;

		@NotNull
		final Stack<T> currentAncestors = new Stack<>();

		final Map<String, T> nodes = new HashMap<>();

		@NotNull
		final String[] rows;

		HierarchyState(@NotNull String[] rows) {
			this.rows = rows;
		}

		C process() {
			for (String row : rows) {
				process(row);
			}
			parentToChildren.forEach(childrenSetter);
			return parentToChildren.get(globalNode());
		}

		void process(@NotNull String currentRow) {

			final int currentIndentation = indentation.getLevelOfIndentation(currentRow);
			final String unindentedString = indentation.getUnindentedString(currentRow);
			if (!nodes.containsKey(unindentedString)) {
				nodes.put(unindentedString, nodeConstructor.apply(unindentedString));
			}
			final T currentNode = nodes.get(unindentedString);

			if (currentIndentation > lastIndentation) {
				if (currentIndentation - lastIndentation > 1) {
					throw new RuntimeException("A child node cannot be indented more than once after its parent.");

				} else {
					// We've gone down another level, so the last node is the new parent.
					currentAncestors.push(lastNode);
					if (!parentToChildren.containsKey(lastNode)) {
						parentToChildren.put(lastNode, childrenConstructor.get());
					}
				}
			} else {
				// We've gone back up at least zero levels, so we need to update the current parent.
				int goBackUp = lastIndentation - currentIndentation;
				for (int i = 0; i < goBackUp; i++) {
					currentAncestors.pop();
				}
			}
			C siblings = parentToChildren.get(currentAncestors.peek());
			if (siblings == null) {
				throw new RuntimeException("The siblings should not be null.  Does the provided "
						+ "childrenConstructor return null?");
			}
			siblings.add(currentNode);

			lastNode = currentNode;
			lastIndentation = currentIndentation;
		}
	}

	@Nullable
	@Contract(value = "-> null", pure = true)
	private T globalNode() {
		return null;
	}
}
