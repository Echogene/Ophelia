package ophelia.event.observable;

/**
 * @author Steven Weston
 */
public abstract class AbstractAsyncObservable<E> extends AbstractObservable<E> {

	protected void fireAsyncEvent(E event) {
		observers.parallelStream()
				.forEach(c -> c.accept(event));
	}
}
