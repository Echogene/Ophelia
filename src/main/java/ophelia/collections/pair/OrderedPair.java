package ophelia.collections.pair;

import org.jetbrains.annotations.NotNull;

/**
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
