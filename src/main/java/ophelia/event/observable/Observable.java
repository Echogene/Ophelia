package ophelia.event.observable;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * An observable can be observed.  It can have observations made about events it fires.
 * @author Steven Weston
 */
public interface Observable<E> {

	void observe(@NotNull Consumer<E> observation);

	void unobserve(@NotNull Consumer<E> observation);

	void unobserveAll();
}
