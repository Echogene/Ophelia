package ophelia.event.publisher;

import ophelia.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * A broadcaster that broadcasts to its subscribers synchronously (though not necessarily in a deterministic order).
 * @author Steven Weston
 */
public interface SynchronousBroadcaster<E extends Event> extends Publisher<E> {

	void broadcast(@NotNull E event);
}
