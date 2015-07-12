package ophelia.event.observable;

/**
 * @author Steven Weston
 */
public abstract class AbstractSynchronousObservable<E> extends AbstractObservable<E> {

	protected void fireEvent(E event) {
		observers.forEach(c -> c.accept(event));
	}
}
