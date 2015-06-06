package ophelia.util;

import org.junit.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static ophelia.util.FunctionUtils.ignoreExceptions;

/**
 * @author Steven Weston
 */
public class CurrentIteratorTest {

	@Test
	public void test_on_empty_set() throws Exception {

		CurrentIterator<Object> iterator = new CurrentIterator<>(Collections.emptySet().iterator());

		assertNull(iterator.current());
	}

	@Test(expected = NoSuchElementException.class)
	public void test_current_after_end() throws Exception {

		CurrentIterator<Object> iterator = new CurrentIterator<>(Collections.emptySet().iterator());

		ignoreExceptions(iterator, Iterator::next);

		iterator.current();
	}

	@Test
	public void test_on_singleton_set() throws Exception {

		String lol = "lol";
		CurrentIterator<String> iterator = new CurrentIterator<>(Collections.singleton(lol).iterator());

		assertNull(iterator.current());
		assertEquals(lol, iterator.next());
		assertEquals(lol, iterator.current());
		assertEquals(lol, iterator.current());
	}
}