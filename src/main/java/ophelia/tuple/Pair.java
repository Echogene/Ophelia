package ophelia.tuple;

import org.jetbrains.annotations.NotNull;

/**
 * A pair of two things that are not null.
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Pair<?, ?> pair = (Pair<?, ?>) o;

		if (!left.equals(pair.left)) return false;
		return right.equals(pair.right);

	}

	@Override
	public int hashCode() {
		int result = left.hashCode();
		result = 31 * result + right.hashCode();
		return result;
	}
}
