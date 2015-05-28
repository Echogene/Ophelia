package ophelia.builder;

import org.jetbrains.annotations.NotNull;

public interface Builder<T, B extends Builder<T, B>> extends SelfReturner<B> {

	@NotNull T build();
}
