package ophelia.collections.bag;

import ophelia.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class HashBag<E> implements ModifiableIntegerBag<E> {

	private Map<E, AtomicInteger> bag = new HashMap<>();

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

	@Override
	public void forEach(@NotNull BiConsumer<E, Integer> consumer) {
		bag.entrySet().forEach(entry -> {
			int value = entry.getValue().get();
			if (value != 0) {
				consumer.accept(entry.getKey(), value);
			}
		});
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
}
