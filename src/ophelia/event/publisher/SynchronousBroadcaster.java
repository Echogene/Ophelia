package ophelia.event.publisher;

import ophelia.event.Event;

/**
 * @author Steven Weston
 */
public interface SynchronousBroadcaster<E extends Event> extends Publisher<E> {

	void broadcast(E event);
}
