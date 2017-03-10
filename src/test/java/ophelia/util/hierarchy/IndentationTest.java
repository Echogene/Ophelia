package ophelia.util.hierarchy;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IndentationTest {

	@Test
	public void indentation_of_string_should_remove_indentation() throws Exception {
		Indentation space = Indentation.ofString(" ");
		Indentation twoSpaces = Indentation.ofString("  ");

		assertThat(space.getUnindentedString(""), is(""));
		assertThat(space.getUnindentedString(" "), is(""));
		assertThat(space.getUnindentedString("  "), is(""));
		assertThat(space.getUnindentedString("   "), is(""));

		assertThat(twoSpaces.getUnindentedString(""), is(""));
		assertThat(twoSpaces.getUnindentedString(" "), is(" "));
		assertThat(twoSpaces.getUnindentedString("  "), is(""));
		assertThat(twoSpaces.getUnindentedString("   "), is(" "));
		assertThat(twoSpaces.getUnindentedString("    "), is(""));
		assertThat(twoSpaces.getUnindentedString("     "), is(" "));

		assertThat(space.getUnindentedString("lol"), is("lol"));
		assertThat(space.getUnindentedString(" lol"), is("lol"));
		assertThat(space.getUnindentedString("  lol"), is("lol"));
		assertThat(space.getUnindentedString("   lol"), is("lol"));

		assertThat(twoSpaces.getUnindentedString("lol"), is("lol"));
		assertThat(twoSpaces.getUnindentedString(" lol"), is(" lol"));
		assertThat(twoSpaces.getUnindentedString("  lol"), is("lol"));
		assertThat(twoSpaces.getUnindentedString("   lol"), is(" lol"));
		assertThat(twoSpaces.getUnindentedString("    lol"), is("lol"));
		assertThat(twoSpaces.getUnindentedString("     lol"), is(" lol"));
	}

	@Test
	public void indentation_of_string_should_count_indentation() throws Exception {
		Indentation space = Indentation.ofString(" ");
		Indentation twoSpaces = Indentation.ofString("  ");

		assertThat(space.getLevelOfIndentation(""), is(0));
		assertThat(space.getLevelOfIndentation(" "), is(1));
		assertThat(space.getLevelOfIndentation("  "), is(2));
		assertThat(space.getLevelOfIndentation("   "), is(3));

		assertThat(twoSpaces.getLevelOfIndentation(""), is(0));
		assertThat(twoSpaces.getLevelOfIndentation(" "), is(0));
		assertThat(twoSpaces.getLevelOfIndentation("  "), is(1));
		assertThat(twoSpaces.getLevelOfIndentation("   "), is(1));
		assertThat(twoSpaces.getLevelOfIndentation("    "), is(2));
		assertThat(twoSpaces.getLevelOfIndentation("     "), is(2));

		assertThat(space.getLevelOfIndentation("lol"), is(0));
		assertThat(space.getLevelOfIndentation(" lol"), is(1));
		assertThat(space.getLevelOfIndentation("  lol"), is(2));
		assertThat(space.getLevelOfIndentation("   lol"), is(3));

		assertThat(twoSpaces.getLevelOfIndentation("lol"), is(0));
		assertThat(twoSpaces.getLevelOfIndentation(" lol"), is(0));
		assertThat(twoSpaces.getLevelOfIndentation("  lol"), is(1));
		assertThat(twoSpaces.getLevelOfIndentation("   lol"), is(1));
		assertThat(twoSpaces.getLevelOfIndentation("    lol"), is(2));
		assertThat(twoSpaces.getLevelOfIndentation("     lol"), is(2));
	}
}