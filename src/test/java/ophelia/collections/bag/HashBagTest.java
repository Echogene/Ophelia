package ophelia.collections.bag;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class HashBagTest {

	@Test
	public void emptiness_should_be_correct() throws Exception {
		HashBag<Integer> bag;

		bag = new HashBag<>();
		assertTrue(bag.isEmpty());

		bag = new HashBag<>(Collections.emptyList());
		assertTrue(bag.isEmpty());

		bag = new HashBag<>();
		bag.addOne(1);
		bag.takeOne(1);
		assertTrue(bag.isEmpty());

		bag = new HashBag<>(Collections.singletonList(2));
		bag.takeOne(2);
		assertTrue(bag.isEmpty());

		bag = new HashBag<>();
		bag.takeOne(1);
		bag.addOne(1);
		assertTrue(bag.isEmpty());
	}

	@Test
	public void size_should_be_correct() throws Exception {
		HashBag<Integer> bag;

		bag = new HashBag<>();
		assertThat(bag.size(), is(0));

		bag = new HashBag<>(Collections.emptyList());
		assertThat(bag.size(), is(0));

		bag = new HashBag<>(Collections.singletonList(2));
		assertThat(bag.size(), is(1));

		bag = new HashBag<>(Arrays.asList(2, 3));
		assertThat(bag.size(), is(2));

		bag = new HashBag<>(Arrays.asList(2, 2));
		assertThat(bag.size(), is(2));

		bag = new HashBag<>(Arrays.asList(1, 1, 2, 2, 3));
		assertThat(bag.size(), is(5));

		bag = new HashBag<>();
		bag.takeOne(2);
		assertThat(bag.size(), is(-1));

		bag = new HashBag<>();
		bag.takeOne(2);
		bag.takeOne(3);
		assertThat(bag.size(), is(-2));

		bag = new HashBag<>();
		bag.modifyNumberOf(2, -2);
		assertThat(bag.size(), is(-2));

		bag = new HashBag<>();
		bag.modifyNumberOf(2, -2);
		bag.modifyNumberOf(3, -2);
		assertThat(bag.size(), is(-4));
	}

	@Test
	public void number_should_be_correct() throws Exception {
		HashBag<Integer> bag;

		bag = new HashBag<>(Collections.singletonList(2));
		assertThat(bag.getNumberOf(2), is(1));
		assertThat(bag.getNumberOf(3), is(0));

		bag = new HashBag<>(Arrays.asList(2, 2));
		assertThat(bag.getNumberOf(2), is(2));

		bag = new HashBag<>();
		bag.takeOne(2);
		assertThat(bag.getNumberOf(2), is(-1));

		bag = new HashBag<>();
		bag.addOne(1);
		bag.takeOne(1);
		assertThat(bag.getNumberOf(1), is(0));
	}
}