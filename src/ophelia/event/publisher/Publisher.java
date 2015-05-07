package ophelia.event.publisher;

import ophelia.event.Event;

import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
public interface Publisher<E extends Event> {

	void subscribe(Consumer<E> eventConsumer);

	void unsubscribe(Consumer<E> eventConsumer);

	void unsubscribeAll();
}
