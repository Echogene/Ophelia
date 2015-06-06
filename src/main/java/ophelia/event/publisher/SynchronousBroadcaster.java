package ophelia.event.publisher;

import ophelia.event.Event;

/**
 * A broadcaster that broadcasts to its subscribers synchronously (though not necessarily in a dereministic order).
 * @author Steven Weston
 */
public interface SynchronousBroadcaster<E extends Event> extends Publisher<E> {

	void broadcast(E event);
}
