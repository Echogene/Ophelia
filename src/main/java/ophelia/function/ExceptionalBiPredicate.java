package ophelia.function;

import java.util.function.BiPredicate;

/**
 * Like {@link BiPredicate}, but can throw an exception.
 */
@FunctionalInterface
public interface ExceptionalBiPredicate<S, T, E extends Exception> {
	boolean test(S s, T t) throws E;
}
