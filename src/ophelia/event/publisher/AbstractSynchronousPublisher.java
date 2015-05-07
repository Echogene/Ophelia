package ophelia.event.publisher;

import ophelia.event.Event;

/**
 * @author Steven Weston
 */
public abstract class AbstractSynchronousPublisher<E extends Event>
		extends AbstractPublisher<E> implements SynchronousBroadcaster<E> {

	@Override
	public void broadcast(E event) {
		subscribers.forEach(c -> c.accept(event));
	}
}
