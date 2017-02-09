package ophelia.collections.bag;

import ophelia.collections.iterator.BaseIterable;
import ophelia.collections.iterator.BaseIterator;
import ophelia.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * An implementation of {@link ModifiableIntegerBag} based on a {@link HashMap}.
 * @param <E> the type of the elements contained in the {@code Bag}
 */
public class HashBag<E> implements ModifiableIntegerBag<E> {

	private Map<E, AtomicInteger> bag = new HashMap<>();

	/**
	 * Create an empty bag.  It has no elements.
	 */
	public HashBag() {}

	/**
	 * Create a bag from a list.  Elements appearing in the list multiple times will appear in the constructed bag
	 * multiple times.
	 */
	public HashBag(@NotNull final List<E> list) {
		list.forEach(this::addOne);
	}

	/**
	 * Create a bag from a {@link BaseIterable}.  Elements appearing in the iterable multiple times will appear in the
	 * constructed bag multiple times.
	 */
	public <I extends BaseIterator<E>> HashBag(@NotNull final BaseIterable<E, I> list) {
		list.forEach(this::addOne);
	}

	@NotNull
	public static <E> HashBag<E> differenceOf(@NotNull final List<E> surplusList, @NotNull final List<E> lackingList) {
		HashBag<E> bag = new HashBag<>();
		surplusList.forEach(bag::addOne);
		lackingList.forEach(bag::takeOne);
		return bag;
	}

	@NotNull
	public static <E, I extends BaseIterator<E>> HashBag<E> differenceOf(
			@NotNull final BaseIterable<E, I> surplusList,
			@NotNull final BaseIterable<E, I> lackingList
	) {
		HashBag<E> bag = new HashBag<>();
		surplusList.forEach(bag::addOne);
		lackingList.forEach(bag::takeOne);
		return bag;
	}

	@NotNull
	public static <E> HashBag<E> of(
			final int number,
			@NotNull final E element
	) {
		HashBag<E> bag = new HashBag<>();
		bag.modifyNumberOf(element, number);
		return bag;
	}

	@NotNull
	@Override
	public Integer getNumberOf(@NotNull E element) {
		if (bag.containsKey(element)) {
			AtomicInteger oldNumber = bag.get(element);
			assert oldNumber != null;
			return oldNumber.get();
		} else {
			return 0;
		}
	}

	@NotNull
	@Override
	public Stream<Pair<E, Integer>> stream() {
		return bag.entrySet().stream()
				.filter(entry -> entry.getValue().get() != 0)
				.map(entry -> new Pair<>(entry.getKey(), entry.getValue().get()));
	}

	@NotNull
	@Override
	public HashBag<E> getDifference(@NotNull BaseBag<? extends E, ? extends Integer> subtrahend) {
		HashBag<E> difference = new HashBag<>();
		forEach(difference::modifyNumberOf);
		subtrahend.forEach((e, copies) -> difference.modifyNumberOf(e, -copies));
		return difference;
	}

	@NotNull
	@Override
	public HashBag<E> getSum(@NotNull BaseBag<? extends E, ? extends Integer> addend) {
		HashBag<E> difference = new HashBag<>();
		forEach(difference::modifyNumberOf);
		addend.forEach(difference::modifyNumberOf);
		return difference;
	}

	@Override
	public void forEach(@NotNull BiConsumer<? super E, ? super Integer> consumer) {
		bag.entrySet().forEach(entry -> {
			int value = entry.getValue().get();
			if (value != 0) {
				consumer.accept(entry.getKey(), value);
			}
		});
	}

	@Override
	public boolean isEmpty() {
		return copiesStream().allMatch(i -> i == 0);
	}

	@Override
	public boolean hasLackingItems() {
		return copiesStream().anyMatch(i -> i < 0);
	}

	@NotNull
	@Override
	public HashBag<E> getLackingItems() {
		HashBag<E> lackingItems = new HashBag<>();
		forEach((e, i) -> {
			if (i < 0) {
				lackingItems.modifyNumberOf(e, i);
			}
		});
		return lackingItems;
	}

	@Override
	public boolean hasSurplusItems() {
		return copiesStream().anyMatch(i -> i > 0);
	}

	@NotNull
	@Override
	public HashBag<E> getSurplusItems() {
		HashBag<E> surplusItems = new HashBag<>();
		forEach((e, i) -> {
			if (i > 0) {
				surplusItems.modifyNumberOf(e, i);
			}
		});
		return surplusItems;
	}

	@NotNull
	@Override
	public Integer modifyNumberOf(@NotNull E element, @NotNull Integer number) {
		if (bag.containsKey(element)) {
			AtomicInteger oldNumber = bag.get(element);
			assert oldNumber != null;
			return oldNumber.getAndAdd(number);
		} else {
			bag.put(element, new AtomicInteger(number));
			return 0;
		}
	}

	@NotNull
	@Override
	public Integer size() {
		return copiesStream().sum();
	}

	private IntStream copiesStream() {
		return bag.values().stream()
				.mapToInt(AtomicInteger::get);
	}

	@NotNull
	@Override
	public HashBag<E> getInverse() {
		HashBag<E> inverse = new HashBag<>();
		forEach((e, n) -> inverse.modifyNumberOf(e, -n));
		return inverse;
	}
}
