package ophelia.collections.pair;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Steven Weston
 */
public class OrderedPair<E> extends AbstractPair<E> {

	private OrderedPair(@NotNull E first, @NotNull E second) {
		super(first, second);
	}

	@NotNull
	@Contract("_, _ -> new")
	public static <E> OrderedPair<E> of(@NotNull E first, @NotNull E second) {
		return new OrderedPair<>(first, second);
	}

	@NotNull
	public E getFirst() {
		return first;
	}

	@NotNull
	public E getSecond() {
		return second;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OrderedPair<?> that = (OrderedPair<?>) o;

		return (first.equals(that.first) && second.equals(that.second));
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}
}
