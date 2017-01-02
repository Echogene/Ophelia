package ophelia.builder;

import org.jetbrains.annotations.NotNull;

/**
 * A builder can return itself and build a new object.
 * @param <T> the type of thing built by this Builder
 * @param <B> the type of the class implementing this interface
 */
public interface Builder<T, B extends Builder<T, B>> extends SelfReturner<B> {

	@NotNull T build();
}
