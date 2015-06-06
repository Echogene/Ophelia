package ophelia.event.observable;

import ophelia.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * An observable can be observed.  It can have observations made about events it fires.
 * @author Steven Weston
 */
public interface Observable<E extends Event> {

	void observe(@NotNull Consumer<E> observation);

	void unobserve(@NotNull Consumer<E> observation);

	void unobserveAll();
}
