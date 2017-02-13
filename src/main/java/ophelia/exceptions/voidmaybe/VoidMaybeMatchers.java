package ophelia.exceptions.voidmaybe;

import ophelia.exceptions.CollectedException;
import ophelia.exceptions.StackedException;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;

public interface VoidMaybeMatchers {

	@NotNull
	static Matcher<VoidMaybe> success() {
		return new BaseMatcher<VoidMaybe>() {
			@Override
			public boolean matches(Object o) {
				if (!(o instanceof VoidMaybe)) {
					return false;
				}
				VoidMaybe maybe = (VoidMaybe) o;
				return maybe.isSuccess();
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("is a success");
			}

			@Override
			public void describeMismatch(Object o, Description description) {
				description.appendText("was ");
				if (o instanceof VoidMaybe) {
					VoidMaybe maybe = (VoidMaybe) o;
					if (!maybe.isSuccess()) {
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
	static <T> Matcher<VoidMaybe> failure(@NotNull Matcher<? super StackedException> matcher) {
		return new BaseMatcher<VoidMaybe>() {
			@Override
			public boolean matches(Object o) {
				if (!(o instanceof VoidMaybe)) {
					return false;
				}
				VoidMaybe maybe = (VoidMaybe) o;
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
				if (o instanceof VoidMaybe) {
					VoidMaybe maybe = (VoidMaybe) o;
					if (maybe.isSuccess()) {
						description.appendText("not a failure");
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
