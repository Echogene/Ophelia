package ophelia.collections.bag;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BagCollectorsTest {

	@Test
	public void should_collect_array_stream_to_bag() throws Exception {
		HashBag<Integer> bag = Arrays.stream(new Integer[] {1, 2, 3, 4, 1, 1, 2, 1})
				.collect(BagCollectors.toBag());

		assertThat(bag.getNumberOf(1), is(4));
		assertThat(bag.getNumberOf(2), is(2));
		assertThat(bag.getNumberOf(3), is(1));
		assertThat(bag.getNumberOf(4), is(1));
		assertThat(bag.getNumberOf(5), is(0));
	}

	@Test
	public void should_collect_map_to_bag() throws Exception {
		HashBag<Integer> bag = new HashMap<Integer, Integer>() {{
			put(1, 4);
			put(2, 2);
			put(3, 1);
			put(4, 1);
			put(5, 0);
			put(6, -1);
		}}
				.entrySet().stream()
						.collect(BagCollectors.toBag(Map.Entry::getKey, Map.Entry::getValue));

		assertThat(bag.getNumberOf(1), is(4));
		assertThat(bag.getNumberOf(2), is(2));
		assertThat(bag.getNumberOf(3), is(1));
		assertThat(bag.getNumberOf(4), is(1));
		assertThat(bag.getNumberOf(5), is(0));
		assertThat(bag.getNumberOf(6), is(-1));
		assertThat(bag.getNumberOf(7), is(0));
	}
}