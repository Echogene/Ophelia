package ophelia.event.publisher;

import ophelia.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A publisher is an object that publishes events.  It can have subscribers to read the events when they are published.
 * @author Steven Weston
 */
public interface Publisher<E extends Event> {

	void subscribe(@NotNull Consumer<E> eventConsumer);

	void unsubscribe(@NotNull Consumer<E> eventConsumer);

	void unsubscribeAll();
}
