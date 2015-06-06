package ophelia.event.publisher;

import ophelia.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public abstract class AbstractAsyncBroadcaster<E extends Event>
		extends AbstractPublisher<E> implements AsyncBroadcaster<E> {

	@Override
	public void asyncBroadcast(@NotNull E event) {
		subscribers.parallelStream()
				.forEach(c -> c.accept(event));
	}
}
