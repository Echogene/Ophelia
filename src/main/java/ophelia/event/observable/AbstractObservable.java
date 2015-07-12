package ophelia.event.observable;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Steven Weston
 */
abstract class AbstractObservable<E> implements Observable<E> {

	final Set<Consumer<E>> observers = new HashSet<>();

	@Override
	public void observe(@NotNull Consumer<E> observer) {
		observers.add(observer);
	}

	@Override
	public void unobserve(@NotNull Consumer<E> observer) {
		observers.remove(observer);
	}

	@Override
	public void unobserveAll() {
		observers.clear();
	}
}
