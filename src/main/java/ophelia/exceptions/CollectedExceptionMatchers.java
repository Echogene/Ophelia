package ophelia.exceptions;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;

public interface CollectedExceptionMatchers {

	@NotNull
	static Matcher<CollectedException> hasException(@NotNull Matcher<? super Exception> matcher) {
		return new BaseMatcher<CollectedException>() {
			@Override
			public boolean matches(Object o) {
				if (!(o instanceof CollectedException)) {
					return false;
				}
				CollectedException e = (CollectedException) o;
				return e.getExceptions().stream()
						.anyMatch(matcher::matches);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("has an exception that ");
				matcher.describeTo(description);
			}

			@Override
			public void describeMismatch(Object o, Description description) {
				if (o instanceof CollectedException) {
					description.appendText("did not have an exception that ");
					matcher.describeTo(description);

				} else {
					description.appendText("was ");
					description.appendValue(o);
				}
			}
		};
	}}
