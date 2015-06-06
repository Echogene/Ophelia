package ophelia.event.publisher;

import ophelia.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public abstract class AbstractSynchronousPublisher<E extends Event>
		extends AbstractPublisher<E> implements SynchronousBroadcaster<E> {

	@Override
	public void broadcast(@NotNull E event) {
		subscribers.forEach(c -> c.accept(event));
	}
}
