package ophelia.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static ophelia.util.function.FunctionUtils.ignoreExceptions;
import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class CurrentIteratorTest {

	@Test
	public void test_on_empty_set() throws Exception {

		CurrentIterator<Object> iterator = new CurrentIterator<>(Collections.emptySet().iterator());

		assertFalse(iterator.hasNext());
		assertNull(iterator.current());
		assertFalse(iterator.hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void test_current_after_end() throws Exception {

		CurrentIterator<Object> iterator = new CurrentIterator<>(Collections.emptySet().iterator());

		ignoreExceptions(iterator, Iterator::next);

		assertFalse(iterator.hasNext());
		iterator.current();
	}

	@Test
	public void test_on_singleton_set() throws Exception {

		String lol = "lol";
		CurrentIterator<String> iterator = new CurrentIterator<>(Collections.singleton(lol).iterator());

		assertTrue(iterator.hasNext());
		assertNull(iterator.current());
		assertTrue(iterator.hasNext());

		assertEquals(lol, iterator.next());

		assertFalse(iterator.hasNext());
		assertEquals(lol, iterator.current());
		assertEquals(lol, iterator.current());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void test_on_set() throws Exception {

		String lol = "lol";
		String rofl = "rofl";
		CurrentIterator<String> iterator = new CurrentIterator<>(Arrays.asList(lol, rofl).iterator());

		assertTrue(iterator.hasNext());
		assertNull(iterator.current());
		assertTrue(iterator.hasNext());

		assertEquals(lol, iterator.next());

		assertTrue(iterator.hasNext());
		assertEquals(lol, iterator.current());
		assertEquals(lol, iterator.current());
		assertTrue(iterator.hasNext());

		assertEquals(rofl, iterator.next());

		assertFalse(iterator.hasNext());
		assertEquals(rofl, iterator.current());
		assertEquals(rofl, iterator.current());
		assertFalse(iterator.hasNext());
	}
}