package ophelia.function;

import java.util.function.Predicate;

/**
 * Like {@link Predicate}, but can throw an exception.
 */
@FunctionalInterface
public interface ExceptionalPredicate<T, E extends Exception> {
	boolean test(T t) throws E;
}
