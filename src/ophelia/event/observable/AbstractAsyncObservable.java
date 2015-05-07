package ophelia.event.observable;

import ophelia.event.Event;

/**
 * @author Steven Weston
 */
public abstract class AbstractAsyncObservable<E extends Event> extends AbstractObservable<E> {

	protected void fireAsyncEvent(E event) {
		observers.parallelStream()
				.forEach(c -> c.accept(event));
	}
}
