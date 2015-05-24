package ophelia.builder;

import org.jetbrains.annotations.NotNull;

public interface Builder<T, B extends Builder<T, B>> {

	@NotNull B noöp();

	@NotNull T build();
}
