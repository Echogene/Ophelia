package ophelia.collections.bag;

import org.junit.Test;

import java.text.MessageFormat;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static ophelia.collections.bag.BagUtils.NOTHING;
import static ophelia.util.NumberUtils.cardinal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BagUtilsTest {

	@Test
	public void should_present_bag_correctly() throws Exception {
		String presentation = BagUtils.presentBag(
				HashBag.differenceOf(
						asList(1, 1, 2),
						asList(3, 3, 4)
				),
				(e, copies) -> MessageFormat.format("{0} {1}s", cardinal(copies), e),
				joining(", ", "You gained ", ", "),
				joining(", ", "but lost ", "."),
				NOTHING
		);
		assertThat(presentation, is("You gained two 1s, one 2s, but lost two 3s, one 4s."));
	}

	@Test
	public void should_present_surplus_emptiness_correctly() throws Exception {
		String presentation = BagUtils.presentBag(
				HashBag.differenceOf(
						emptyList(),
						asList(3, 3, 4)
				),
				(e, copies) -> MessageFormat.format("{0} {1}s", cardinal(copies), e),
				joining(", ", "You gained ", ", "),
				joining(", ", "but lost ", "."),
				NOTHING
		);
		assertThat(presentation, is("You gained nothing, but lost two 3s, one 4s."));
	}

	@Test
	public void should_present_lacking_emptiness_correctly() throws Exception {
		String presentation = BagUtils.presentBag(
				HashBag.differenceOf(
						asList(1, 1, 2),
						emptyList()
				),
				(e, copies) -> MessageFormat.format("{0} {1}s", cardinal(copies), e),
				joining(", ", "You gained ", ", "),
				joining(", ", "but lost ", "."),
				NOTHING
		);
		assertThat(presentation, is("You gained two 1s, one 2s, but lost nothing."));
	}
}