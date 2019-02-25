package ophelia.collections.matchers;

import ophelia.collections.iterator.BaseIterable;
import ophelia.collections.iterator.BaseIterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Like {@link org.hamcrest.core.IsCollectionContaining}, but for Ophelia iterables (specifically
 * {@link BaseIterable}s).
 * @see org.hamcrest.core.IsCollectionContaining
 * @param <T> the type contained within the matched iterable
 */
public class IsCollectionContaining<T> extends TypeSafeDiagnosingMatcher<BaseIterable<? super T, ?>> {

	private final Matcher<? super T> elementMatcher;

	public IsCollectionContaining(Matcher<? super T> elementMatcher) {
		this.elementMatcher = elementMatcher;
	}

	@Override
	protected boolean matchesSafely(BaseIterable<? super T, ?> collection, Description mismatchDescription) {
		boolean isPastFirst = false;
		for (BaseIterator<? super T> iterator = collection.iterator(); iterator.hasNext(); ) {
			Object item = iterator.next();
			if (elementMatcher.matches(item)){
				return true;
			}
			if (isPastFirst) {
				mismatchDescription.appendText(", ");
			}
			elementMatcher.describeMismatch(item, mismatchDescription);
			isPastFirst = true;
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description
				.appendText("a collection containing ")
				.appendDescriptionOf(elementMatcher);
	}

	@Factory
	public static <T> Matcher<BaseIterable<? super T, ?>> hasItem(Matcher<? super T> itemMatcher) {
		return new IsCollectionContaining<>(itemMatcher);
	}

	@Factory
	public static <T> Matcher<BaseIterable<? super T, ?>> hasItem(T item) {
		return hasItem(equalTo(item));
	}

	@Factory
	public static <T> Matcher<BaseIterable<T, ?>> hasItems(Matcher<? super T>... itemMatchers) {
		return allOf(Arrays.<Matcher<? super T>>stream(itemMatchers)
				.map(m -> new IsCollectionContaining<T>(m))
				.collect(Collectors.<Matcher<? super BaseIterable<T, ?>>>toList()));
	}

	@Factory
	public static <T> Matcher<BaseIterable<T, ?>> hasItems(T... items) {
		return allOf(Arrays.stream(items)
				.map(IsCollectionContaining::hasItem)
				.collect(Collectors.toList()));
	}
}
