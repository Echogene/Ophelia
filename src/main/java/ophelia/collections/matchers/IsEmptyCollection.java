package ophelia.collections.matchers;

import ophelia.collections.BaseCollection;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Like {@link org.hamcrest.collection.IsEmptyCollection}, but for Ophelia {@link BaseCollection}s.
 * @param <E> the type of the elements in the collection
 */
public class IsEmptyCollection<E> extends TypeSafeMatcher<BaseCollection<? extends E>> {

	@Override
	public boolean matchesSafely(@NotNull BaseCollection<? extends E> collection) {
		return collection.isEmpty();
	}

	@Override
	public void describeMismatchSafely(@Nullable BaseCollection<? extends E> collection, @NotNull Description mismatchDescription) {
		mismatchDescription.appendValue(collection);
	}

	@Override
	public void describeTo(@NotNull Description description) {
		description.appendText("an empty collection");
	}

	@NotNull
	@Factory
	public static <E> Matcher<BaseCollection<? extends E>> empty() {
		return new IsEmptyCollection<>();
	}

	@NotNull
	@Factory
	public static <E> Matcher<BaseCollection<E>> emptyCollectionOf(@NotNull Class<E> type) {
		//noinspection unchecked
		return (Matcher<BaseCollection<E>>) (Matcher) empty();
	}
}
