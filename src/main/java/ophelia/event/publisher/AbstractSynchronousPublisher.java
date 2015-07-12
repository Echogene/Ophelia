package ophelia.event.publisher;

import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public abstract class AbstractSynchronousPublisher<E>
		extends AbstractPublisher<E> implements SynchronousBroadcaster<E> {

	@Override
	public void broadcast(@NotNull E event) {
		subscribers.forEach(c -> c.accept(event));
	}
}
