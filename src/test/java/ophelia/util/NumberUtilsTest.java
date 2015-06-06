package ophelia.util;

import org.junit.Test;

import static ophelia.util.NumberUtils.ordinal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Steven Weston
 */
public class NumberUtilsTest {

	@Test
	public void testOrdinal() {
		assertThat(ordinal(0), is("0th"));
		assertThat(ordinal(1), is("1st"));
		assertThat(ordinal(2), is("2nd"));
		assertThat(ordinal(3), is("3rd"));
		assertThat(ordinal(4), is("4th"));
		assertThat(ordinal(11), is("11th"));
		assertThat(ordinal(12), is("12th"));
		assertThat(ordinal(13), is("13th"));
		assertThat(ordinal(100), is("100th"));
		assertThat(ordinal(101), is("101st"));
		assertThat(ordinal(102), is("102nd"));
		assertThat(ordinal(103), is("103rd"));
		assertThat(ordinal(104), is("104th"));
		assertThat(ordinal(111), is("111th"));
		assertThat(ordinal(112), is("112th"));
		assertThat(ordinal(113), is("113th"));
	}
}
