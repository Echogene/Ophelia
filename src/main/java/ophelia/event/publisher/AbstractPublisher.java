package ophelia.event.publisher;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
abstract class AbstractPublisher<E> implements Publisher<E> {

	final Set<Consumer<E>> subscribers = new HashSet<>();

	@Override
	public void subscribe(@NotNull Consumer<E> eventConsumer) {
		subscribers.add(eventConsumer);
	}

	@Override
	public void unsubscribe(@NotNull Consumer<E> eventConsumer) {
		subscribers.remove(eventConsumer);
	}

	@Override
	public void unsubscribeAll() {
		subscribers.clear();
	}
}
