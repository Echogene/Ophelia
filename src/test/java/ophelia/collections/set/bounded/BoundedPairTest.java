package ophelia.collections.set.bounded;

import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BoundedPairTest {

	@Test
	public void testFirstConstructor() throws Exception {
		BoundedPair<String> pair = new BoundedPair<>("lol");

		assertTrue(pair.contains("lol"));
		assertTrue(pair.containsAll(singleton("lol")));
		assertThat(pair.size(), is(1));
	}

	@Test
	public void testSecondConstructor() throws Exception {
		BoundedPair<String> pair = new BoundedPair<>("lol", "wut");

		assertTrue(pair.contains("lol"));
		assertTrue(pair.contains("wut"));
		assertTrue(pair.containsAll(asList("lol", "wut")));
		assertThat(pair.size(), is(2));
	}

	@Test
	public void testAdd() throws Exception {
		BoundedPair<String> pair = new BoundedPair<>();

		assertTrue(pair.add("lol"));
		assertTrue(pair.contains("lol"));
		assertTrue(pair.containsAll(singleton("lol")));
		assertThat(pair.size(), is(1));

		assertFalse(pair.add("lol"));
		assertTrue(pair.contains("lol"));
		assertTrue(pair.containsAll(singleton("lol")));
		assertThat(pair.size(), is(1));

		assertTrue(pair.add("wut"));
		assertTrue(pair.contains("lol"));
		assertTrue(pair.contains("wut"));
		assertTrue(pair.containsAll(asList("lol", "wut")));
		assertThat(pair.size(), is(2));
	}

	@Test
	public void testAddOverflow() throws Exception {
		BoundedPair<String> pair = new BoundedPair<>("lol", "wut");

		assertFalse(pair.add("lol"));
		assertTrue(pair.contains("lol"));
		assertTrue(pair.contains("wut"));
		assertTrue(pair.containsAll(asList("lol", "wut")));
		assertThat(pair.size(), is(2));

		try {
			pair.add("rofl");
			fail();
		} catch (BoundedSetOverflowException e) {
			assertTrue(pair.contains("lol"));
			assertTrue(pair.contains("wut"));
			assertFalse(pair.contains("rofl"));
			assertTrue(pair.containsAll(asList("lol", "wut")));
			assertThat(pair.size(), is(2));
		}
	}

	@Test
	public void testRemoveFirst() throws Exception {
		BoundedPair<String> pair = new BoundedPair<>("lol", "wut");

		assertTrue(pair.remove("lol"));
		assertTrue(pair.contains("wut"));
		assertTrue(pair.containsAll(singleton("wut")));
		assertThat(pair.size(), is(1));
	}

	@Test
	public void testRemoveSecond() throws Exception {
		BoundedPair<String> pair = new BoundedPair<>("lol", "wut");

		assertTrue(pair.remove("wut"));
		assertTrue(pair.contains("lol"));
		assertTrue(pair.containsAll(singleton("lol")));
		assertThat(pair.size(), is(1));
	}

	@Test
	public void testRemoveNeither() throws Exception {
		BoundedPair<String> pair = new BoundedPair<>("lol", "wut");

		assertFalse(pair.remove("rofl"));
		assertTrue(pair.contains("lol"));
		assertTrue(pair.contains("wut"));
		assertTrue(pair.containsAll(asList("lol", "wut")));
		assertThat(pair.size(), is(2));
	}
}