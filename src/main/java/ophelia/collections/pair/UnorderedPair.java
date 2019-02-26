package ophelia.collections.pair;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public class UnorderedPair<E> extends AbstractPair<E> {

	private UnorderedPair(@NotNull E first, @NotNull E second) {
		super(first, second);
	}

	@NotNull
	@Contract("_, _ -> new")
	public static <E> UnorderedPair<E> of(@NotNull E first, @NotNull E second) {
		return new UnorderedPair<>(first, second);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UnorderedPair<?> that = (UnorderedPair<?>) o;

		return (first.equals(that.first) && second.equals(that.second))
				|| (first.equals(that.second) && second.equals(that.first));
	}

	@Override
	public int hashCode() {
		return first.hashCode() ^ second.hashCode();
	}
}
