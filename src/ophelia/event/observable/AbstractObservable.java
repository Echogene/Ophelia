package ophelia.event.observable;

import ophelia.event.Event;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
abstract class AbstractObservable<E extends Event> implements Observable<E> {

	final Set<Consumer<E>> observers = new HashSet<>();

	@Override
	public void observe(Consumer<E> observer) {
		observers.add(observer);
	}

	@Override
	public void unobserve(Consumer<E> observer) {
		observers.remove(observer);
	}

	@Override
	public void unobserveAll() {
		observers.clear();
	}
}
