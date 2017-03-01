package ophelia.function;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

/**
 * Like {@link BiConsumer} but can accept three arguments.
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {
	void accept(T t, U u, V v);

	@NotNull
	default TriConsumer<T, U, V> andThen(@NotNull TriConsumer<? super T, ? super U, ? super V> after) {
		return (t, u, v) -> {
			accept(t, u, v);
			after.accept(t, u, v);
		};
	}
}
