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
import java.util.Collection;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Like {@link org.hamcrest.collection.IsIterableContainingInAnyOrder}, but for Ophelia iterables (specifically
 * {@link BaseIterable}s).
 * @see org.hamcrest.collection.IsIterableContainingInAnyOrder
 * @param <T> the type contained within the matched iterable
 */
public class IsIterableContainingInAnyOrder<T> extends TypeSafeDiagnosingMatcher<BaseIterable<? extends T, ?>> {
	
	@NotNull
	private final Collection<Matcher<? super T>> matchers;

	public IsIterableContainingInAnyOrder(@NotNull Collection<Matcher<? super T>> matchers) {
		this.matchers = matchers;
	}
	
	@Override
	protected boolean matchesSafely(@NotNull BaseIterable<? extends T, ?> items, @NotNull Description mismatchDescription) {
		Matching<T> matching = new Matching<>(matchers, mismatchDescription);
		BaseIterator<? extends T> iterator = items.iterator();
		while (iterator.hasNext()) {
			T item = iterator.next();
			if (!matching.matches(item)) {
				return false;
			}
		}
		
		return matching.isFinished(items);
	}
	
	@Override
	public void describeTo(@NotNull Description description) {
		description.appendText("iterable over ")
			.appendList("[", ", ", "]", matchers)
			.appendText(" in any order");
	}

	private static class Matching<S> {

		@NotNull
		private final Collection<Matcher<? super S>> matchers;

		@NotNull
		private final Description mismatchDescription;

		public Matching(@NotNull Collection<Matcher<? super S>> matchers, @NotNull Description mismatchDescription) {
			this.matchers = new ArrayList<>(matchers);
			this.mismatchDescription = mismatchDescription;
		}
		
		public boolean matches(@Nullable S item) {
		return isNotSurplus(item) && isMatched(item);
		}

		@SuppressWarnings("unchecked")
		public boolean isFinished(@NotNull BaseIterable<? extends S, ?> items) {
			if (matchers.isEmpty()) {
				return true;
			}
			mismatchDescription
					.appendText("No item matches: ").appendList("", ", ", "", matchers)
					.appendText(" in ").appendValueList("[", ", ", "]", items);
			return false;
		}
		
		private boolean isNotSurplus(@Nullable S item) {
			if (matchers.isEmpty()) {
				mismatchDescription.appendText("Not matched: ").appendValue(item);
				return false;
			}
			return true;
		}

		private boolean isMatched(@Nullable S item) {
			for (Matcher<? super S> matcher : matchers) {
				if (matcher.matches(item)) {
				matchers.remove(matcher);
				return true;
				}
			}
			mismatchDescription.appendText("Not matched: ").appendValue(item);
			return false;
		}
	}

	/**
	 * @see org.hamcrest.collection.IsIterableContainingInAnyOrder#containsInAnyOrder(Matcher[])
	 */
	@SafeVarargs
	@NotNull
	@Factory
	public static <T> Matcher<BaseIterable<? extends T, ?>> containsInAnyOrder(@NotNull Matcher<? super T>... itemMatchers) {
		return containsInAnyOrder(Arrays.asList(itemMatchers));
	}

	/**
	 * @see IsIterableContainingInAnyOrder#containsInAnyOrder(Object[])
	 */
	@SafeVarargs
	@NotNull
	@Factory
	public static <T> Matcher<BaseIterable<? extends T, ?>> containsInAnyOrder(@NotNull T... items) {
		List<Matcher<? super T>> matchers = new ArrayList<>();
		for (T item : items) {
			matchers.add(equalTo(item));
		}
		
		return new IsIterableContainingInAnyOrder<>(matchers);
	}

	/**
	 * @see IsIterableContainingInAnyOrder#containsInAnyOrder(Collection)
	 */
	@NotNull
	@Factory
	public static <T> Matcher<BaseIterable<? extends T, ?>> containsInAnyOrder(@NotNull Collection<Matcher<? super T>> itemMatchers) {
		return new IsIterableContainingInAnyOrder<>(itemMatchers);
	}
}

