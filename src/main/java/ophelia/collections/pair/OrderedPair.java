package ophelia.collections.pair;

import org.jetbrains.annotations.NotNull;

/**
 * todo: the hash code and equals need overriding so that they aren't symmetrical
 * @author Steven Weston
 */
public class OrderedPair<E> extends AbstractPair<E> {

	public OrderedPair(@NotNull E first, @NotNull E second) {
		super(first, second);
	}

	@NotNull
	public E getFirst() {
		return first;
	}

	@NotNull
	public E getSecond() {
		return second;
	}
}
