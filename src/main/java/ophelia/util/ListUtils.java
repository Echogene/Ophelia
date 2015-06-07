package ophelia.util;

import ophelia.exceptions.maybe.Maybe;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.NoSuchElementException;

import static ophelia.exceptions.maybe.Maybe.maybe;

/**
 * @author Steven Weston
 */
public class ListUtils {

	/**
	 * Avoid throwing an error when accessing an out-of-bounds index in a list by returning null in that case.
	 * @param list The list whose item we are getting.
	 * @param index The index of the element we want.
	 * @param <T> The type of the elements in the list.
	 * @return The (index)th element of the list or null if the index is out-of-bounds.
	 */
	@Contract("null, _ -> null")
	@Nullable
	public static <T> T safeGet(@Nullable List<T> list, int index) {
		if (list == null || index >= list.size() || index < 0) {
			return null;
		}
		return list.get(index);
	}

	/**
	 * Get rid of the first and last members of a list.
	 */
	@NotNull
	public static <T> List<T> stripFirstAndLast(@NotNull List<T> list) {
		return list.subList(1, list.size() - 1);
	}

	/**
	 * @param list the list of which we want the first entry
	 * @param <T> the type of the entries in the list
	 * @return the first element of the given list, which may be null because lists can contain null entries
	 * @throws NoSuchElementException if the given list is empty
	 */
	@Nullable
	public static <T> T first(@NotNull List<T> list) {
		return getIfNotEmpty(list, 0);
	}

	@NotNull
	public static <T> Maybe<T> maybeFirst(@NotNull List<T> list) {
		return maybe(() -> first(list));
	}

	/**
	 * @param list the list of which we want the last entry
	 * @param <T> the type of the entries in the list
	 * @return the last element of the given list, which may be null because lists can contain null entries
	 * @throws NoSuchElementException if the given list is empty
	 */
	@Nullable
	public static <T> T last(@NotNull List<T> list) {
		return getIfNotEmpty(list, -1);
	}

	@NotNull
	public static <T> Maybe<T> maybeLast(@NotNull List<T> list) {
		return maybe(() -> last(list));
	}

	/**
	 * @param list the list of which we want an entry
	 * @param index the index in the list of the entry we want.  If it is less than zero, it gets from the end of the
	 *              list instead e.g. -1 gets the last element of the list, -2 gets the penultimate &c.
	 * @param <T> the type of the entries in the list
	 * @return the (index)th or, if index < 0, the (size+index)th element of the list
	 * @throws NoSuchElementException if the given list is empty
	 */
	@Nullable
	public static <T> T getIfNotEmpty(@NotNull List<T> list, int index) {
		if (list.isEmpty()) {
			throw new NoSuchElementException();

		} else if (index < 0) {
			return list.get(list.size() + index);

		} else {
			return list.get(index);
		}
	}

}
