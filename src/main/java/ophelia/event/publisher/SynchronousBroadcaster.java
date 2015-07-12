package ophelia.event.publisher;

import org.jetbrains.annotations.NotNull;

/**
 * A broadcaster that broadcasts to its subscribers synchronously (though not necessarily in a deterministic order).
 * @author Steven Weston
 */
public interface SynchronousBroadcaster<E> extends Publisher<E> {

	void broadcast(@NotNull E event);
}
