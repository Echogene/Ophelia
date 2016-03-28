package ophelia.util;

import ophelia.exceptions.maybe.Maybe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
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

	public static <T> Collector<T, List<T>, Maybe<T>> findUnique() {
		return Collector.of(
				ArrayList::new,
				List::add,
				(left, right) -> { left.addAll(right); return left; },
				list -> {
					if (list.isEmpty()) {
						return Maybe.failure(new EmptyException());
					} else if (list.size() > 1) {
						return Maybe.failure(new NonUniqueException(list));
					} else {
						return Maybe.success(list.get(0));
					}
				}
		);
	}

	private static class EmptyException extends Exception {
	}

	private static class NonUniqueException extends Exception {
		<T> NonUniqueException(List<T> list) {
			super(list.toString());
		}
	}
}
