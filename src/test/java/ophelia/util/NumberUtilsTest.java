package ophelia.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static ophelia.util.NumberUtils.cardinal;
import static ophelia.util.NumberUtils.ordinal;
import static org.hamcrest.core.Is.is;

/**
 * @author Steven Weston
 */
public class NumberUtilsTest {

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Test
	public void testOrdinal() {
		collector.checkThat(ordinal(0), is("0th"));
		collector.checkThat(ordinal(1), is("1st"));
		collector.checkThat(ordinal(2), is("2nd"));
		collector.checkThat(ordinal(3), is("3rd"));
		collector.checkThat(ordinal(4), is("4th"));
		collector.checkThat(ordinal(11), is("11th"));
		collector.checkThat(ordinal(12), is("12th"));
		collector.checkThat(ordinal(13), is("13th"));
		collector.checkThat(ordinal(100), is("100th"));
		collector.checkThat(ordinal(101), is("101st"));
		collector.checkThat(ordinal(102), is("102nd"));
		collector.checkThat(ordinal(103), is("103rd"));
		collector.checkThat(ordinal(104), is("104th"));
		collector.checkThat(ordinal(111), is("111th"));
		collector.checkThat(ordinal(112), is("112th"));
		collector.checkThat(ordinal(113), is("113th"));
	}

	@Test
	public void testCardinal() {
		collector.checkThat(cardinal(0), is("zero"));
		collector.checkThat(cardinal(1), is("one"));
		collector.checkThat(cardinal(2), is("two"));
		collector.checkThat(cardinal(3), is("three"));
		collector.checkThat(cardinal(4), is("four"));
		collector.checkThat(cardinal(5), is("five"));
		collector.checkThat(cardinal(6), is("six"));
		collector.checkThat(cardinal(7), is("seven"));
		collector.checkThat(cardinal(8), is("eight"));
		collector.checkThat(cardinal(9), is("nine"));
		collector.checkThat(cardinal(10), is("ten"));
		collector.checkThat(cardinal(11), is("eleven"));
		collector.checkThat(cardinal(12), is("twelve"));
		collector.checkThat(cardinal(13), is("thirteen"));
		collector.checkThat(cardinal(14), is("fourteen"));
		collector.checkThat(cardinal(15), is("fifteen"));
		collector.checkThat(cardinal(16), is("sixteen"));
		collector.checkThat(cardinal(17), is("seventeen"));
		collector.checkThat(cardinal(18), is("eighteen"));
		collector.checkThat(cardinal(19), is("nineteen"));
		collector.checkThat(cardinal(20), is("twenty"));
		collector.checkThat(cardinal(21), is("twenty-one"));
		collector.checkThat(cardinal(30), is("thirty"));
		collector.checkThat(cardinal(31), is("thirty-one"));
		collector.checkThat(cardinal(40), is("forty"));
		collector.checkThat(cardinal(41), is("forty-one"));
		collector.checkThat(cardinal(50), is("fifty"));
		collector.checkThat(cardinal(51), is("fifty-one"));
		collector.checkThat(cardinal(60), is("sixty"));
		collector.checkThat(cardinal(61), is("sixty-one"));
		collector.checkThat(cardinal(70), is("seventy"));
		collector.checkThat(cardinal(71), is("seventy-one"));
		collector.checkThat(cardinal(80), is("eighty"));
		collector.checkThat(cardinal(81), is("eighty-one"));
		collector.checkThat(cardinal(90), is("ninety"));
		collector.checkThat(cardinal(91), is("ninety-one"));
		collector.checkThat(cardinal(100), is("one hundred"));
		collector.checkThat(cardinal(101), is("one hundred and one"));
		collector.checkThat(cardinal(102), is("one hundred and two"));
		collector.checkThat(cardinal(103), is("one hundred and three"));
		collector.checkThat(cardinal(104), is("one hundred and four"));
		collector.checkThat(cardinal(110), is("one hundred and ten"));
		collector.checkThat(cardinal(111), is("one hundred and eleven"));
		collector.checkThat(cardinal(112), is("one hundred and twelve"));
		collector.checkThat(cardinal(113), is("one hundred and thirteen"));
		collector.checkThat(cardinal(200), is("two hundred"));
		collector.checkThat(cardinal(201), is("two hundred and one"));
		collector.checkThat(cardinal(300), is("three hundred"));
		collector.checkThat(cardinal(301), is("three hundred and one"));
		collector.checkThat(cardinal(400), is("four hundred"));
		collector.checkThat(cardinal(401), is("four hundred and one"));
		collector.checkThat(cardinal(500), is("five hundred"));
		collector.checkThat(cardinal(501), is("five hundred and one"));
		collector.checkThat(cardinal(600), is("six hundred"));
		collector.checkThat(cardinal(601), is("six hundred and one"));
		collector.checkThat(cardinal(700), is("seven hundred"));
		collector.checkThat(cardinal(701), is("seven hundred and one"));
		collector.checkThat(cardinal(800), is("eight hundred"));
		collector.checkThat(cardinal(801), is("eight hundred and one"));
		collector.checkThat(cardinal(900), is("nine hundred"));
		collector.checkThat(cardinal(901), is("nine hundred and one"));
		collector.checkThat(cardinal(999), is("nine hundred and ninety-nine"));
		collector.checkThat(cardinal(1_000), is("one thousand"));
		collector.checkThat(cardinal(1_001), is("one thousand and one"));
		collector.checkThat(cardinal(1_099), is("one thousand and ninety-nine"));
		collector.checkThat(cardinal(1_100), is("one thousand, one hundred"));
		collector.checkThat(cardinal(1_999), is("one thousand, nine hundred and ninety-nine"));
		collector.checkThat(cardinal(2_000), is("two thousand"));
		collector.checkThat(cardinal(2_099), is("two thousand and ninety-nine"));
		collector.checkThat(cardinal(3_000), is("three thousand"));
		collector.checkThat(cardinal(4_000), is("four thousand"));
		collector.checkThat(cardinal(5_000), is("five thousand"));
		collector.checkThat(cardinal(6_000), is("six thousand"));
		collector.checkThat(cardinal(7_000), is("seven thousand"));
		collector.checkThat(cardinal(8_000), is("eight thousand"));
		collector.checkThat(cardinal(9_000), is("nine thousand"));
		collector.checkThat(cardinal(9_999), is("nine thousand, nine hundred and ninety-nine"));
		collector.checkThat(cardinal(10_000), is("ten thousand"));
		collector.checkThat(cardinal(11_000), is("eleven thousand"));
		collector.checkThat(cardinal(99_999), is("ninety-nine thousand, nine hundred and ninety-nine"));
		collector.checkThat(cardinal(999_999), is("nine hundred and ninety-nine thousand, nine hundred and ninety-nine"));
	}
}
