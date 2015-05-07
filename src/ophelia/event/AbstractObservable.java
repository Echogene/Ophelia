package ophelia.event;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
public abstract class AbstractObservable<E extends Event> implements Observable<E> {

	private final Set<Consumer<E>> observers = new HashSet<>();

	@Override
	public void observe(Consumer<E> observer) {
		observers.add(observer);
	}

	@Override
	public void unobserve(Consumer<E> observer) {
		observers.remove(observer);
	}

	@Override
	public void unobserveAll() {
		observers.clear();
	}

	protected void fireEvent(E event) {
		observers.forEach(c -> c.accept(event));
	}

	protected void fireAsyncEvent(E event) {
		observers.parallelStream()
				.forEach(c -> c.accept(event));
	}
}
