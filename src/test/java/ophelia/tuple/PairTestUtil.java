package ophelia.tuple;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public class PairTestUtil {

	@NotNull
	public static Matcher<Pair<?, ?>> isEqual() {
		return new BaseMatcher<Pair<?, ?>>() {
			@Override
			public boolean matches(Object item) {
				if (!(item instanceof Pair)) {
					return false;
				}
				Pair pair = (Pair) item;
				return pair.getLeft().equals(pair.getRight());
			}

			@Override
			public void describeMismatch(Object item, Description description) {
				if (!(item instanceof Pair)) {
					description.appendText("is not a Pair");
					return;
				}
				Pair pair = (Pair) item;
				description.appendValue(pair.getLeft());
				description.appendText(" is not ");
				description.appendValue(pair.getRight());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("is equal");
			}
		};
	}
}