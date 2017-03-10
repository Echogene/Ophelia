package ophelia.util.hierarchy;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static ophelia.util.hierarchy.TreeUtils.prettyPrint;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class HierarchyParserTest {

	@Test
	public void should_parse_empty_hierarchy() throws Exception {
		List<Foo> hierarchy = HierarchyParser.forChildList(Foo::new, Foo::setChildren)
				.parse();

		assertThat(hierarchy, is(empty()));
	}

	@Test
	public void should_parse_one_root_node() throws Exception {
		List<Foo> hierarchy = HierarchyParser.forChildList(Foo::new, Foo::setChildren)
				.parse("lol");

		assertThat(hierarchy, hasSize(1));

		Foo lol = hierarchy.get(0);
		assertThat(lol.bar, is("lol"));
		assertThat(lol.children, is(empty()));
	}

	@Test
	public void should_parse_two_root_nodes() throws Exception {
		List<Foo> hierarchy = HierarchyParser.forChildList(Foo::new, Foo::setChildren)
				.parse(
						"lol",
						"rofl"
				);

		assertThat(hierarchy, hasSize(2));

		Foo lol = hierarchy.get(0);
		assertThat(lol.bar, is("lol"));
		assertThat(lol.children, is(empty()));

		Foo rofl = hierarchy.get(1);
		assertThat(rofl.bar, is("rofl"));
		assertThat(rofl.children, is(empty()));
	}

	@Test
	public void should_parse_a_root_node_and_its_child() throws Exception {
		List<Foo> hierarchy = HierarchyParser.forChildList(Foo::new, Foo::setChildren)
				.parse(
						"lol",
						" rofl"
				);

		assertThat(hierarchy, hasSize(1));

		Foo lol = hierarchy.get(0);
		assertThat(lol.bar, is("lol"));
		assertThat(lol.children, hasSize(1));

		Foo rofl = lol.children.get(0);
		assertThat(rofl.bar, is("rofl"));
		assertThat(rofl.children, is(empty()));
	}

	@Test
	public void should_parse_a_root_node_and_its_two_children() throws Exception {
		List<Foo> hierarchy = HierarchyParser.forChildList(Foo::new, Foo::setChildren)
				.parse(
						"lol",
						" rofl",
						" garply"
				);

		assertThat(hierarchy, hasSize(1));

		Foo lol = hierarchy.get(0);
		assertThat(lol.bar, is("lol"));
		assertThat(lol.children, hasSize(2));

		Foo rofl = lol.children.get(0);
		assertThat(rofl.bar, is("rofl"));
		assertThat(rofl.children, is(empty()));

		Foo garply = lol.children.get(1);
		assertThat(garply.bar, is("garply"));
		assertThat(garply.children, is(empty()));
	}

	@Test
	public void should_parse_a_root_node_child_and_grandchild() throws Exception {
		List<Foo> hierarchy = HierarchyParser.forChildList(Foo::new, Foo::setChildren)
				.parse(
						"lol",
						" rofl",
						"  garply"
				);

		assertThat(hierarchy, hasSize(1));

		Foo lol = hierarchy.get(0);
		assertThat(lol.bar, is("lol"));
		assertThat(lol.children, hasSize(1));

		Foo rofl = lol.children.get(0);
		assertThat(rofl.bar, is("rofl"));
		assertThat(rofl.children, hasSize(1));

		Foo garply = rofl.children.get(0);
		assertThat(garply.bar, is("garply"));
		assertThat(garply.children, is(empty()));
	}

	@Test
	public void should_parse_two_root_nodes_where_the_second_one_has_a_child() throws Exception {
		List<Foo> hierarchy = HierarchyParser.forChildList(Foo::new, Foo::setChildren)
				.parse(
						"lol",
						"rofl",
						" garply"
				);

		assertThat(hierarchy, hasSize(2));

		Foo lol = hierarchy.get(0);
		assertThat(lol.bar, is("lol"));
		assertThat(lol.children, is(empty()));

		Foo rofl = hierarchy.get(1);
		assertThat(rofl.bar, is("rofl"));
		assertThat(rofl.children, hasSize(1));

		Foo garply = rofl.children.get(0);
		assertThat(garply.bar, is("garply"));
		assertThat(garply.children, is(empty()));
	}

	@Test
	public void should_parse_a_root_node_with_two_children_where_the_first_itself_has_a_child() throws Exception {
		List<Foo> hierarchy = HierarchyParser.forChildList(Foo::new, Foo::setChildren)
				.parse(
						"lol",
						" rofl",
						"  garply",
						" qux"
				);

		assertThat(hierarchy, hasSize(1));

		Foo lol = hierarchy.get(0);
		assertThat(lol.bar, is("lol"));
		assertThat(lol.children, hasSize(2));

		Foo rofl = lol.children.get(0);
		assertThat(rofl.bar, is("rofl"));
		assertThat(rofl.children, hasSize(1));

		Foo garply = rofl.children.get(0);
		assertThat(garply.bar, is("garply"));
		assertThat(garply.children, is(empty()));

		Foo qux = lol.children.get(1);
		assertThat(qux.bar, is("qux"));
		assertThat(qux.children, is(empty()));
	}

	@Test
	public void should_parse_a_root_node_with_two_children_where_the_second_itself_has_a_child() throws Exception {
		List<Foo> hierarchy = HierarchyParser.forChildList(Foo::new, Foo::setChildren)
				.parse(
						"lol",
						" rofl",
						" garply",
						"  qux"
				);

		assertThat(hierarchy, hasSize(1));

		Foo lol = hierarchy.get(0);
		assertThat(lol.bar, is("lol"));
		assertThat(lol.children, hasSize(2));

		Foo rofl = lol.children.get(0);
		assertThat(rofl.bar, is("rofl"));
		assertThat(rofl.children, is(empty()));

		Foo garply = lol.children.get(1);
		assertThat(garply.bar, is("garply"));
		assertThat(garply.children, hasSize(1));

		Foo qux = garply.children.get(0);
		assertThat(qux.bar, is("qux"));
		assertThat(qux.children, is(empty()));
	}

	@Test
	public void complex_hierarchy_should_look_alright() throws Exception {
		List<Foo> hierarchy = HierarchyParser.forChildList(Foo::new, Foo::setChildren)
				.parse(
						"lol",
						" rofl",
						"  wibble",
						"   wobble",
						"    wubble",
						" garply",
						"  qux",
						" gorply",
						"  qix",
						"   qax",
						"   frog",
						"fred",
						" frobble"
				);

		String pretty = prettyPrint(hierarchy, foo -> foo.bar, foo -> foo.children, 1);
		assertThat(pretty, is (
				"lol\n" +
				"├─rofl\n" +
				"│ └─wibble\n" +
				"│   └─wobble\n" +
				"│     └─wubble\n" +
				"├─garply\n" +
				"│ └─qux\n" +
				"└─gorply\n" +
				"  └─qix\n" +
				"    ├─qax\n" +
				"    └─frog\n" +
				"fred\n" +
				"└─frobble\n"
		));
	}

	class Foo {
		@NotNull
		final String bar;

		@NotNull
		List<Foo> children = emptyList();

		Foo(@NotNull String bar) {
			this.bar = bar;
		}

		void setChildren(@NotNull List<Foo> children) {
			this.children = children;
		}
	}
}