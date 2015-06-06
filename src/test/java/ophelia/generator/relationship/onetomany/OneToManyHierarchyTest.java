package ophelia.generator.relationship.onetomany;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * @author Steven Weston
 */
public class OneToManyHierarchyTest {

	@Test
	public void test_set_parent_maintains_parent_children() throws Exception {

		GeneratedHierarchyMember root = new GeneratedHierarchyMember();

		GeneratedHierarchyMember child = new GeneratedHierarchyMember();
		child.setParent(root);

		assertThat(root.getChildren().getUnmodifiableInnerSet(), contains(child));

		child.setParent(null);

		assertThat(root.getChildren().getUnmodifiableInnerSet(), is(empty()));
	}

	@Test
	public void test_add_remove_child_maintains_child_parent() throws Exception {

		GeneratedHierarchyMember root = new GeneratedHierarchyMember();

		GeneratedHierarchyMember child = new GeneratedHierarchyMember();
		root.addChild(child);

		assertThat(child.getParent(), is(root));

		root.removeChild(child);

		assertThat(child.getParent(), is(nullValue()));
	}

	@Test
	public void test_remove_all_children_maintains_child_parent() throws Exception {

		GeneratedHierarchyMember root = new GeneratedHierarchyMember();

		GeneratedHierarchyMember child = new GeneratedHierarchyMember();
		root.addChild(child);

		GeneratedHierarchyMember child2 = new GeneratedHierarchyMember();
		root.addChild(child2);

		assertThat(child.getParent(), is(root));
		assertThat(child2.getParent(), is(root));

		root.removeAllChildren();

		assertThat(child.getParent(), is(nullValue()));
		assertThat(child2.getParent(), is(nullValue()));
	}
}
