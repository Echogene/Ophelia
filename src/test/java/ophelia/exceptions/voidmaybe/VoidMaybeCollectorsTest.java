package ophelia.exceptions.voidmaybe;

import ophelia.exceptions.maybe.MaybeCollectorsTest;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static ophelia.exceptions.CollectedExceptionMatchers.hasException;
import static ophelia.exceptions.voidmaybe.VoidMaybeCollectors.merge;
import static ophelia.exceptions.voidmaybe.VoidMaybeMatchers.failure;
import static ophelia.exceptions.voidmaybe.VoidMaybeMatchers.success;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

public class VoidMaybeCollectorsTest {

	@Test
	public void empty_set_should_not_be_unique() throws Exception {
		VoidMaybe unique = Stream.<VoidMaybe>empty().collect(merge());

		assertThat(unique, failure(hasException(is(instanceOf(NoSuchElementException.class)))));
	}

	@Test
	public void single_failure_should_not_be_unique() throws Exception {
		VoidMaybe failure = VoidMaybe.failure(new NullPointerException());
		VoidMaybe unique = Stream.of(failure).collect(merge());

		assertThat(unique, is(failure));
	}

	@Test
	public void multiple_failures_should_not_be_unique() throws Exception {
		VoidMaybe failure = VoidMaybe.failure(new NullPointerException());
		VoidMaybe failure2 = VoidMaybe.failure(new IllegalArgumentException());

		VoidMaybe unique = Stream.of(failure, failure2).collect(merge());

		assertThat(unique, failure(hasException(hasException(is(instanceOf(NullPointerException.class))))));
		assertThat(unique, failure(hasException(hasException(is(instanceOf(IllegalArgumentException.class))))));
	}

	/**
	 * @see MaybeCollectorsTest#multiple_successes_for_the_same_value_should_be_unique
	 */
	@Test
	public void multiple_successes_should_be_unique() throws Exception {
		VoidMaybe success = VoidMaybe.success();
		VoidMaybe success2 = VoidMaybe.success();

		VoidMaybe unique = Stream.of(success, success2).collect(merge());

		assertThat(unique, success());
	}

	@Test
	public void unique_success_should_be_unique() throws Exception {
		VoidMaybe success = VoidMaybe.success();

		VoidMaybe unique = Stream.of(success).collect(merge());

		assertThat(unique, success());
	}

	@Test
	public void unique_success_with_some_failures_should_be_unique() throws Exception {
		VoidMaybe success = VoidMaybe.success();
		VoidMaybe failure = VoidMaybe.failure(new NullPointerException());
		VoidMaybe failure2 = VoidMaybe.failure(new IllegalArgumentException());

		VoidMaybe unique = Stream.of(success, failure, failure2).collect(merge());

		assertThat(unique, success());
	}

}