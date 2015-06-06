package ophelia.event.publisher;

import ophelia.event.Event;

/**
 * A broadcaster that broadcasts to its subscribers asynchronously.
 * @author Steven Weston
 */
public interface AsyncBroadcaster<E extends Event> extends Publisher<E> {

	void asyncBroadcast(E event);
}
