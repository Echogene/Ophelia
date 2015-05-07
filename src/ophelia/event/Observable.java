package ophelia.event;

import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
public interface Observable<E extends Event> {

	void observe(E event, Consumer<E> observation);

	void unobserve(E event, Consumer<E> observation);

	void unobserveAll(E event);
}
