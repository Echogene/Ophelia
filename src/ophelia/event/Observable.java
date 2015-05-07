package ophelia.event;

import ophelia.util.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An observable can be observed.  It can have observations made about events it fires.
 * @author Steven Weston
 */
public class Observable<E extends Event> {

	private final Map<E, List<Consumer<E>>> observations = new HashMap<>();

	public void observe(E event, Consumer<E> observation) {
		MapUtils.updateListBasedMap(observations, event, observation);
	}

	public void unobserve(E event, Consumer<E> observation) {
		observations.get(event).remove(observation);
	}

	public void unobserveAll(E event) {
		observations.get(event).clear();
	}

	protected void fireEvent(E event) {

		List<Consumer<E>> consumers = observations.get(event);
		if (consumers == null) {
			return;
		}
		for (Consumer<E> consumer : consumers) {
			consumer.accept(event);
		}
	}
}
