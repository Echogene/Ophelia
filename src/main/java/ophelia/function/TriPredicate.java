package ophelia.function;

import java.util.function.BiPredicate;

/**
 * Like {@link BiPredicate} but can test three things at once.
 */
@FunctionalInterface
public interface TriPredicate<R, S, T> {

	boolean test(R r, S s, T t);
}
