package ophelia.superposition;

import ophelia.exceptions.AmbiguityException;
import ophelia.function.ExceptionalConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Represents a superposition of multiple objects.  When observed, some elements may no longer be valid (collapsion).
 * The invalid elements are stored by their reason of failure (the exception that was thrown for them).
 * @author Steven Weston
 */
public class Superposition<T, E extends Exception> {

	private final Set<T> superposition = new HashSet<>();
	private final Map<T, E> collapsion = new HashMap<>();

	public Superposition(Set<T> initialPossibilities) {
		superposition.addAll(initialPossibilities);
	}

	/**
	 * Observe all elements of the superposition with the given observer.  If the observer throws an exception for any
	 * value, then they are removed from the superposition
	 * @param observer the consumer that will observe all members of the current superposition
	 */
	@SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "unchecked"})
	public void observe(@NotNull ExceptionalConsumer<T, E> observer) {

		Iterator<T> superpositionIterator = superposition.iterator();
		while (superpositionIterator.hasNext()) {
			T superposee = superpositionIterator.next();
			try {
				observer.accept(superposee);
				
			} catch (Exception e) {
				superpositionIterator.remove();
				// Because we cannot catch generic Exceptions (even though we can throw them) *shakes fist*, we need to
				// cast the Exception to the type it obviously is.
				collapsion.put(superposee, (E) e);
			}
		}
	}

	/**
	 * @return whether there are no longer any possible values for the superposition.
	 */
	public boolean isEmpty() {
		return superposition.isEmpty();
	}

	/**
	 * @return whether there is only one possibility left.
	 */
	public boolean isUnique() {
		return 1 == superposition.size();
	}

	/**
	 * @return the unique possible value left in the superposition.
	 * @throws AmbiguityException if there is more than one possible value
	 */
	public T getUniqueValue() throws AmbiguityException {
		if (!isUnique()) {
			throw new AmbiguityException();
		} else {
			return superposition.iterator().next();
		}
	}

	/**
	 * @return the set of still possible values
	 */
	@NotNull
	public Set<T> getSuperposition() {
		return Collections.unmodifiableSet(superposition);
	}

	/**
	 * @return the map of failed values to their reason for failure
	 */
	@NotNull
	public Map<T, E> getCollapsion() {
		return Collections.unmodifiableMap(collapsion);
	}
}
