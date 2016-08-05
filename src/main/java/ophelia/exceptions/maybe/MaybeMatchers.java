package ophelia.exceptions.maybe;

import ophelia.exceptions.CollectedException;
import ophelia.exceptions.StackedException;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;

public interface MaybeMatchers {

	@NotNull
	static <T> Matcher<Maybe<T>> success(@NotNull Matcher<? super T> matcher) {
		return new BaseMatcher<Maybe<T>>() {
			@Override
			public boolean matches(Object o) {
				if (!(o instanceof Maybe)) {
					return false;
				}
				Maybe<?> maybe = (Maybe) o;
				return maybe.isSuccess() && matcher.matches(maybe.returnOnSuccess().nullOnFailure());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("is a success and ");
				matcher.describeTo(description);
			}

			@Override
			public void describeMismatch(Object o, Description description) {
				description.appendText("was ");
				if (o instanceof Maybe) {
					Maybe<?> maybe = (Maybe) o;
					if (maybe.isSuccess()) {
						description.appendText("a success but ");
						matcher.describeMismatch(maybe.returnOnSuccess().nullOnFailure(), description);
					} else {
						description.appendText("not a success: ");
						description.appendText(maybe.getException().map(CollectedException::getMessage).orElse(""));
					}
				} else {
					description.appendValue(o);
				}
			}
		};
	}

	@NotNull
	static <T> Matcher<Maybe<T>> failure(@NotNull Matcher<? super StackedException> matcher) {
		return new BaseMatcher<Maybe<T>>() {
			@Override
			public boolean matches(Object o) {
				if (!(o instanceof Maybe)) {
					return false;
				}
				Maybe<?> maybe = (Maybe) o;
				return !maybe.isSuccess() && matcher.matches(maybe.getException().orElse(null));
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("is a failure and ");
				matcher.describeTo(description);
			}

			@Override
			public void describeMismatch(Object o, Description description) {
				description.appendText("was ");
				if (o instanceof Maybe) {
					Maybe<?> maybe = (Maybe) o;
					if (maybe.isSuccess()) {
						description.appendText("not a failure: ");
						description.appendValue(maybe.returnOnSuccess().nullOnFailure());
					} else {
						description.appendText("a failure but ");
						matcher.describeMismatch(maybe.getException().orElse(null), description);
					}
				} else {
					description.appendValue(o);
				}
			}
		};
	}
}
