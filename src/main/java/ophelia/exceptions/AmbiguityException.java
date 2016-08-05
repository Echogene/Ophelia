package ophelia.exceptions;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Indicates that a Collection was expected to have exactly one member, but didn't.
 * @author Steven Weston
 */
public class AmbiguityException extends Exception {

	public AmbiguityException() {
		super();
	}

	public <T> AmbiguityException(
			@NotNull String message,
			@NotNull Collection<T> ambiguousCollection,
			@NotNull Function<? super T, String> presenter
	) {
		super(MessageFormat.format(
				message,
				ambiguousCollection.stream()
						.map(presenter)
						.collect(Collectors.joining(", ", "[", "]"))
		));
	}

	public AmbiguityException(@NotNull String message, @NotNull Collection<?> ambiguousCollection) {
		this(message, ambiguousCollection, Object::toString);
	}
}
