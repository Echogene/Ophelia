package ophelia.collections.bag;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;

public interface BagCollectors {

	@NotNull
	static <E> Collector<E, ?, ModifiableIntegerBag<E>> toBag() {
		return new Collector<E, HashBag<E>, ModifiableIntegerBag<E>>() {

			@Override
			public Supplier<HashBag<E>> supplier() {
				return HashBag::new;
			}

			@Override
			public BiConsumer<HashBag<E>, E> accumulator() {
				return ModifiableIntegerBag::addOne;
			}

			@Override
			public BinaryOperator<HashBag<E>> combiner() {
				return (left, right) -> {
					left.merge(right);
					return left;
				};
			}

			@Override
			public Function<HashBag<E>, ModifiableIntegerBag<E>> finisher() {
				return e -> e;
			}

			@Override
			public Set<Characteristics> characteristics() {
				return Collections.emptySet();
			}
		};
	}

	@NotNull
	static <T, E> Collector<T, ?, ModifiableIntegerBag<E>> toBag(
			@NotNull Function<? super T, ? extends E> elementMapper,
			@NotNull ToIntFunction<? super T> intMapper
	) {
		return new Collector<T, HashBag<E>, ModifiableIntegerBag<E>>() {

			@Override
			public Supplier<HashBag<E>> supplier() {
				return HashBag::new;
			}

			@Override
			public BiConsumer<HashBag<E>, T> accumulator() {
				return (bag, t) -> bag.modifyNumberOf(elementMapper.apply(t), intMapper.applyAsInt(t));
			}

			@Override
			public BinaryOperator<HashBag<E>> combiner() {
				return (left, right) -> {
					left.merge(right);
					return left;
				};
			}

			@Override
			public Function<HashBag<E>, ModifiableIntegerBag<E>> finisher() {
				return e -> e;
			}

			@Override
			public Set<Characteristics> characteristics() {
				return Collections.emptySet();
			}
		};
	}
}
