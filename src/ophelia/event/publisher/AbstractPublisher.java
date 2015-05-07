package ophelia.event.publisher;

import ophelia.event.Event;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
abstract class AbstractPublisher<E extends Event> implements Publisher<E> {

	final Set<Consumer<E>> subscribers = new HashSet<>();

	@Override
	public void subscribe(Consumer<E> eventConsumer) {
		subscribers.add(eventConsumer);
	}

	@Override
	public void unsubscribe(Consumer<E> eventConsumer) {
		subscribers.remove(eventConsumer);
	}

	@Override
	public void unsubscribeAll() {
		subscribers.clear();
	}
}
