package ophelia.event.publisher;

import org.jetbrains.annotations.NotNull;

/**
 * A broadcaster that broadcasts to its subscribers asynchronously.
 * @author Steven Weston
 */
public interface AsyncBroadcaster<E> extends Publisher<E> {

	void asyncBroadcast(@NotNull E event);
}
