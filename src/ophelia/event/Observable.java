package ophelia.event;

import java.util.function.Consumer;

/**
 * An observable can be observed.  It can have observations made about events it fires.
 * @author Steven Weston
 */
public interface Observable<E extends Event> {

	void observe(Consumer<E> observation);

	void unobserve(Consumer<E> observation);

	void unobserveAll();
}
