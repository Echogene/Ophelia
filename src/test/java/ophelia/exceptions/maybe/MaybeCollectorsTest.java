package ophelia.exceptions.maybe;

import ophelia.exceptions.AmbiguityException;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static ophelia.exceptions.CollectedExceptionMatchers.hasException;
import static ophelia.exceptions.maybe.MaybeCollectors.toUniqueSuccess;
import static ophelia.exceptions.maybe.MaybeMatchers.failure;
import static ophelia.exceptions.maybe.MaybeMatchers.success;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

public class MaybeCollectorsTest {

	@Test
	public void empty_set_should_not_be_unique() throws Exception {
		Maybe<String> unique = Stream.<Maybe<String>>empty().collect(toUniqueSuccess());

		assertThat(unique, failure(hasException(is(instanceOf(NoSuchElementException.class)))));
	}

	@Test
	public void single_failure_should_not_be_unique() throws Exception {
		Maybe<String> failure = Maybe.failure(new NullPointerException());
		Maybe<String> unique = Stream.of(failure).collect(toUniqueSuccess());

		assertThat(unique, is(failure));
	}

	@Test
	public void multiple_failures_should_not_be_unique() throws Exception {
		Maybe<String> failure = Maybe.failure(new NullPointerException());
		Maybe<String> failure2 = Maybe.failure(new IllegalArgumentException());

		Maybe<String> unique = Stream.of(failure, failure2).collect(toUniqueSuccess());

		assertThat(unique, failure(hasException(hasException(is(instanceOf(NullPointerException.class))))));
		assertThat(unique, failure(hasException(hasException(is(instanceOf(IllegalArgumentException.class))))));
	}

	@Test
	public void multiple_successes_should_not_be_unique() throws Exception {
		Maybe<String> success = Maybe.success("lol");
		Maybe<String> success2 = Maybe.success("rofl");

		Maybe<String> unique = Stream.of(success, success2).collect(toUniqueSuccess());

		assertThat(unique, failure(hasException(is(instanceOf(AmbiguityException.class)))));
	}

	@Test
	public void unique_success_should_be_unique() throws Exception {
		Maybe<String> success = Maybe.success("lol");

		Maybe<String> unique = Stream.of(success).collect(toUniqueSuccess());

		assertThat(unique, success(is("lol")));
	}

	@Test
	public void unique_success_with_some_failures_should_be_unique() throws Exception {
		Maybe<String> success = Maybe.success("lol");
		Maybe<String> failure = Maybe.failure(new NullPointerException());
		Maybe<String> failure2 = Maybe.failure(new IllegalArgumentException());

		Maybe<String> unique = Stream.of(success, failure, failure2).collect(toUniqueSuccess());

		assertThat(unique, success(is("lol")));
	}
}