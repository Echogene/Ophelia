package ophelia.event.publisher;

import ophelia.event.Event;

/**
 * @author Steven Weston
 */
public interface AsyncBroadcaster<E extends Event> extends Publisher<E> {

	void asyncBroadcast(E event);
}
