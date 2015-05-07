package ophelia.event.observable;

import ophelia.event.Event;

/**
 * @author Steven Weston
 */
public abstract class SynchronousAbstractObservable<E extends Event> extends AbstractObservable<E> {

	protected void fireEvent(E event) {
		observers.forEach(c -> c.accept(event));
	}
}
