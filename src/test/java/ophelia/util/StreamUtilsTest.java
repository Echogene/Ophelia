package ophelia.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class StreamUtilsTest {

	@Test
	public void test_consume_two_streams_in_parallel() throws Exception {

		List<String> lol = Arrays.asList("a", "b", "c");
		List<String> wut = Arrays.asList("α", "β", "γ");

		StreamUtils.consume(lol.stream(), wut.stream(), (s, t) -> System.out.println(s + " " + t));
	}
}