package ophelia.collections.matchers;

import ophelia.collections.iterator.BaseIterable;
import ophelia.collections.iterator.BaseIterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Like {@link org.hamcrest.collection.IsIterableContainingInOrder}, but for Ophelia iterables (specifically
 * {@link BaseIterable}s).
 * @see org.hamcrest.collection.IsIterableContainingInOrder
 * @param <T> the type contained within the matched iterable
 */
public class IsIterableContainingInOrder<T> extends TypeSafeDiagnosingMatcher<BaseIterable<? extends T, ?>> {

	@NotNull
	private final List<Matcher<? super T>> matchers;

	public IsIterableContainingInOrder(@NotNull List<Matcher<? super T>> matchers) {
		this.matchers = matchers;
	}

	@Override
	protected boolean matchesSafely(@NotNull BaseIterable<? extends T, ?> iterable, @NotNull Description mismatchDescription) {
		MatchSeries<T> matchSeries = new MatchSeries<>(matchers, mismatchDescription);
		BaseIterator<? extends T> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			T next = iterator.next();
			if (!matchSeries.matches(next)) {
				return false;
			}
		}

		return matchSeries.isFinished();
	}

	@Override
	public void describeTo(@NotNull Description description) {
		description.appendText("iterable containing ").appendList("[", ", ", "]", matchers);
	}

	private static class MatchSeries<U> {

		@NotNull
		public final List<Matcher<? super U>> matchers;

		@NotNull
		private final Description mismatchDescription;

		public int nextMatchIx = 0;

		public MatchSeries(@NotNull List<Matcher<? super U>> matchers, @NotNull Description mismatchDescription) {
			this.mismatchDescription = mismatchDescription;
			if (matchers.isEmpty()) {
				throw new IllegalArgumentException("Should specify at least one expected element");
			}
			this.matchers = matchers;
		}

		public boolean matches(@Nullable U item) {
			return isNotSurplus(item) && isMatched(item);
		}

		public boolean isFinished() {
			if (nextMatchIx < matchers.size()) {
				mismatchDescription.appendText("No item matched: ").appendDescriptionOf(matchers.get(nextMatchIx));
				return false;
			}
			return true;
		}

		private boolean isMatched(@Nullable U item) {
			Matcher<? super U> matcher = matchers.get(nextMatchIx);
			if (!matcher.matches(item)) {
				describeMismatch(matcher, item);
				return false;
			}
			nextMatchIx++;
			return true;
		}

		private boolean isNotSurplus(@Nullable U item) {
			if (matchers.size() <= nextMatchIx) {
				mismatchDescription.appendText("Not matched: ").appendValue(item);
				return false;
			}
			return true;
		}

		private void describeMismatch(@NotNull Matcher<? super U> matcher, @Nullable U item) {
			mismatchDescription.appendText("item " + nextMatchIx + ": ");
			matcher.describeMismatch(item, mismatchDescription);
		}
	}

	/**
	 * @see org.hamcrest.collection.IsIterableContainingInOrder#contains(Object[])
	 */
	@SafeVarargs
	@NotNull
	@Factory
	public static <T> Matcher<BaseIterable<? extends T, ?>> contains(@NotNull T... items) {
		List<Matcher<? super T>> matchers = new ArrayList<>();
		for (T item : items) {
			matchers.add(equalTo(item));
		}

		return contains(matchers);
	}

	/**
	 * @see org.hamcrest.collection.IsIterableContainingInOrder#contains(Matcher)
	 */
	@SuppressWarnings("unchecked")
	@NotNull
	@Factory
	public static <T> Matcher<BaseIterable<? extends T, ?>> contains(@NotNull final Matcher<? super T> itemMatcher) {
		return contains(new ArrayList<Matcher<? super T>>(singletonList(itemMatcher)));
	}

	/**
	 * @see org.hamcrest.collection.IsIterableContainingInOrder#contains(Matcher[])
	 */
	@SafeVarargs
	@NotNull
	@Factory
	public static <T> Matcher<BaseIterable<? extends T, ?>> contains(@NotNull Matcher<? super T>... itemMatchers) {
		return contains(Arrays.<Matcher<? super T>>asList(itemMatchers));
	}


	/**
	 * @see org.hamcrest.collection.IsIterableContainingInOrder#contains(List)
	 */
	@NotNull
	@Factory
	public static <T> Matcher<BaseIterable<? extends T, ?>> contains(@NotNull final List<Matcher<? super T>> itemMatchers) {
		return new IsIterableContainingInOrder<>(itemMatchers);
	}
}
