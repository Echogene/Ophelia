package ophelia.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author Steven Weston
 */
public class StreamUtils {

	/**
	 * Consume two streams in parallel with a biconsumer until one of the streams ends.
	 */
	public static <S, T> void consume(
			@NotNull Stream<S> left,
			@NotNull Stream<T> right,
			@NotNull BiConsumer<? super S, ? super T> consumer
	) {

		Spliterator<S> leftSpliterator = left.spliterator();
		Spliterator<T> rightSpliterator = right.spliterator();

		//noinspection StatementWithEmptyBody
		while (leftSpliterator.tryAdvance(s -> rightSpliterator.tryAdvance(t -> consumer.accept(s, t))));
	}

	public static <T> Collector<T, List<T>, Optional<T>> findUnique() {
		return Collector.of(
				ArrayList::new,
				List::add,
				(left, right) -> { left.addAll(right); return left; },
				list -> {
					if (list.size() != 1) {
						return Optional.empty();
					}
					return Optional.ofNullable(list.get(0));
				}
		);
	}
}
