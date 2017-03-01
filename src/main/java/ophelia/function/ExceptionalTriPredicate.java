package ophelia.function;

/**
 * Like {@link TriPredicate}, but can throw an exception.
 */
@FunctionalInterface
public interface ExceptionalTriPredicate<R, S, T, E extends Exception> {
	boolean test(R r, S s, T t) throws E;
}
