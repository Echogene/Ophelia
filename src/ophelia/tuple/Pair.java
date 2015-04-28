package ophelia.tuple;

import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public class Pair<S, T> {

	private final @NotNull S left;
	private final @NotNull T right;

	public Pair(@NotNull S left, @NotNull T right) {
		this.left = left;
		this.right = right;
	}

	public @NotNull S getLeft() {
		return left;
	}

	public @NotNull T getRight() {
		return right;
	}
}
