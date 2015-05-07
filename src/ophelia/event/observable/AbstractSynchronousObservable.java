package ophelia.event.observable;

import ophelia.event.Event;

/**
 * @author Steven Weston
 */
public abstract class AbstractSynchronousObservable<E extends Event> extends AbstractObservable<E> {

	protected void fireEvent(E event) {
		observers.forEach(c -> c.accept(event));
	}
}
