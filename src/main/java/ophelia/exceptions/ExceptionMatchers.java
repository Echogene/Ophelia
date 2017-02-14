package ophelia.exceptions;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;

import static org.hamcrest.core.IsEqual.equalTo;

public interface ExceptionMatchers {

	@NotNull
	static Matcher<Exception> hasMessage(@NotNull Matcher<? super String> matcher) {
		return new BaseMatcher<Exception>() {
			@Override
			public boolean matches(Object o) {
				if (!(o instanceof Exception)) {
					return false;
				}
				Exception e = (Exception) o;
				return matcher.matches(e.getMessage());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("has a message that ");
				matcher.describeTo(description);
			}

			@Override
			public void describeMismatch(Object o, Description description) {
				if (o instanceof Exception) {
					description.appendText("did not have a message that ");
					matcher.describeTo(description);
					description.appendText(":\n\t");
					Exception exception = (Exception) o;
					matcher.describeMismatch(exception.getMessage(), description);

				} else {
					description.appendText("was ");
					description.appendValue(o);
				}
			}
		};
	}

	@NotNull
	static Matcher<Exception> hasMessage(@NotNull String message) {
		return hasMessage(equalTo(message));
	}
}
