package ophelia.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public class NumberUtils {

	@NotNull
	@Contract(pure = true)
	public static String ordinal(final int integer) {
		int modOneHundred = integer % 100;
		int tens = modOneHundred / 10;
		if (tens == 1) {
			return integer + "th";
		} else {
			int units = modOneHundred % 10;
			switch (units) {
				case 1:
					return integer + "st";
				case 2:
					return integer + "nd";
				case 3:
					return integer + "rd";
				default:
					return integer + "th";
			}
		}
	}

	/**
	 * todo: support numbers after 999,999
	 */
	@NotNull
	@Contract(pure = true)
	public static String cardinal(final int integer) {
		final int modOneMillion = integer % 1_000_000;
		final int hundredsOfThousands = modOneMillion / 1000;
		final int modOneThousand = integer % 1_000;
		final int hundreds = modOneThousand / 100;
		final int modOneHundred = integer % 100;
		final int tens = modOneHundred / 10;
		final int units = modOneHundred % 10;

		String hundredsSuffix;
		if (tens == 0) {
			hundredsSuffix = cardinalOfUnits(units);
		} else if (tens == 1) {
			hundredsSuffix = cardinalOfTeens(modOneHundred);
		} else {
			if (units == 0) {
				hundredsSuffix = cardinalOfTens(tens * 10);
			} else {
				hundredsSuffix = cardinalOfTens(tens * 10) + "-" + cardinalOfUnits(units);
			}
		}

		String hundredsPrefix;
		if (hundreds == 0) {
			hundredsPrefix = "";
		} else if (modOneHundred == 0) {
			hundredsSuffix = "";
			hundredsPrefix = cardinalOfUnits(hundreds) + " hundred";
		} else {
			hundredsPrefix = cardinalOfUnits(hundreds) + " hundred and ";
		}

		final String thousandsPrefix;
		if (hundredsOfThousands == 0) {
			thousandsPrefix = "";
		} else if (modOneThousand == 0) {
			hundredsSuffix = "";
			thousandsPrefix = cardinal(hundredsOfThousands) + " thousand";
		} else if (hundreds == 0) {
			thousandsPrefix = cardinal(hundredsOfThousands) + " thousand and ";
		} else {
			thousandsPrefix = cardinal(hundredsOfThousands) + " thousand, ";
		}

		return thousandsPrefix + hundredsPrefix + hundredsSuffix;
	}

	@NotNull
	private static String cardinalOfUnits(final int units) {
		switch (units) {
			case 0:
				return "zero";
			case 1:
				return "one";
			case 2:
				return "two";
			case 3:
				return "three";
			case 4:
				return "four";
			case 5:
				return "five";
			case 6:
				return "six";
			case 7:
				return "seven";
			case 8:
				return "eight";
			case 9:
				return "nine";
		}
		throw new IllegalArgumentException("Argument must be between 0 and 9 (inclusive), but was " + units);
	}

	@NotNull
	private static String cardinalOfTeens(final int teens) {
		switch (teens) {
			case 10:
				return "ten";
			case 11:
				return "eleven";
			case 12:
				return "twelve";
			case 13:
				return "thirteen";
			case 15:
				return "fifteen";
			case 18:
				return "eighteen";
			case 14:
			case 16:
			case 17:
			case 19:
				return cardinalOfUnits(teens % 10) + "teen";
		}
		throw new IllegalArgumentException("Argument must be between 10 and 19 (inclusive), but was " + teens);
	}

	@NotNull
	private static String cardinalOfTens(final int tens) {
		switch (tens) {
			case 0:
				return "zero";
			case 10:
				return "ten";
			case 20:
				return "twenty";
			case 30:
				return "thirty";
			case 40:
				return "forty";
			case 50:
				return "fifty";
			case 80:
				return "eighty";
			case 60:
			case 70:
			case 90:
				return cardinalOfUnits(tens / 10) + "ty";
		}
		throw new IllegalArgumentException("Argument must be a multiple of ten between 0 and 90 (inclusive), but was " + tens);
	}

	@Contract(pure = true)
	public static boolean isEven(final int integer) {
		return 0 == (integer % 2);
	}

	@Contract(pure = true)
	public static boolean isOdd(final int integer) {
		return 1 == (integer % 2);
	}
}
