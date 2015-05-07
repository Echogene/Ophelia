package ophelia.event;

import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
public interface Broadcaster<E extends Event> {

	void subscribe(Consumer<E> eventConsumer);

	void broadcast(E event);

	void unsubscribe(Consumer<E> eventConsumer);

	void unsubscribeAll();
}
