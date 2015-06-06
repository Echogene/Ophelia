package ophelia.event.publisher;

import ophelia.event.Event;

import java.util.function.Consumer;

/**
 * A publisher is an object that publishes events.  It can have subscribers to read the events when they are published.
 * @author Steven Weston
 */
public interface Publisher<E extends Event> {

	void subscribe(Consumer<E> eventConsumer);

	void unsubscribe(Consumer<E> eventConsumer);

	void unsubscribeAll();
}
