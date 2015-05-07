package ophelia.event;

import ophelia.util.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
public abstract class AbstractObservable<E extends Event> implements Observable<E> {

	private final Map<E, List<Consumer<E>>> observers = new HashMap<>();

	@Override
	public void observe(E event, Consumer<E> observer) {
		MapUtils.updateListBasedMap(observers, event, observer);
	}

	@Override
	public void unobserve(E event, Consumer<E> observer) {
		observers.get(event).remove(observer);
	}

	@Override
	public void unobserveAll(E event) {
		observers.get(event).clear();
	}

	protected void fireEvent(E event) {

		List<Consumer<E>> observers = this.observers.get(event);
		if (observers == null) {
			return;
		}
		for (Consumer<E> observer : observers) {
			observer.accept(event);
		}
	}
}
