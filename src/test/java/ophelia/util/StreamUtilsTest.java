package ophelia.util;

import ophelia.collections.pair.UnorderedPair;
import ophelia.collections.set.EmptySet;
import ophelia.collections.set.Singleton;
import ophelia.exceptions.maybe.Maybe;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * @author Steven Weston
 */
public class StreamUtilsTest {

	@Test
	public void test_consume_two_streams_in_parallel() throws Exception {

		List<String> lol = Arrays.asList("a", "b", "c");
		List<String> wut = Arrays.asList("α", "β", "γ");

		String expected = "aαbβcγ";
		StringBuilder actual = new StringBuilder();

		StreamUtils.consume(lol.stream(), wut.stream(), (s, t) -> actual.append(s).append(t));

		assertThat(actual.toString(), is(expected));
	}

	@Test
	public void test_consume_two_streams_in_parallel_with_left_shorter() throws Exception {

		List<String> lol = Arrays.asList("a", "b", "c");
		List<String> wut = Arrays.asList("α", "β", "γ", "δ");

		String expected = "aαbβcγ";
		StringBuilder actual = new StringBuilder();

		StreamUtils.consume(lol.stream(), wut.stream(), (s, t) -> actual.append(s).append(t));

		assertThat(actual.toString(), is(expected));
	}

	@Test
	public void test_consume_two_streams_in_parallel_with_right_shorter() throws Exception {

		List<String> lol = Arrays.asList("a", "b", "c", "d");
		List<String> wut = Arrays.asList("α", "β", "γ");

		String expected = "aαbβcγ";
		StringBuilder actual = new StringBuilder();

		StreamUtils.consume(lol.stream(), wut.stream(), (s, t) -> actual.append(s).append(t));

		assertThat(actual.toString(), is(expected));
	}

	@Test
	public void should_not_find_unique_for_empty_collection() {
		Maybe<Object> notPresent = EmptySet.emptySet().stream()
				.collect(StreamUtils.findUnique());
		assertThat(notPresent.isSuccess(), is(false));
	}

	@Test
	public void should_find_unique_for_singleton() {
		Maybe<Object> present = new Singleton<>(1).stream()
				.collect(StreamUtils.findUnique());
		assertThat(present.isSuccess(), is(true));
		assertThat(present.returnOnSuccess().nullOnFailure(), is(1));
	}

	@Test
	public void should_find_unique_for_singleton_containing_null() {
		Maybe<Object> present = Collections.singleton(null).stream()
				.collect(StreamUtils.findUnique());
		assertThat(present.isSuccess(), is(true));
		assertThat(present.returnOnSuccess().defaultOnFailure(1), is(nullValue()));
	}

	@Test
	public void should_not_find_unique_for_payr() {
		Maybe<Object> notPresent = UnorderedPair.of(1, 2).stream()
				.collect(StreamUtils.findUnique());
		assertThat(notPresent.isSuccess(), is(false));
	}
}