package ophelia.collections.pair;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class PairTest {

    @Test
    public void ordered_pair_should_have_different_hash_code_depending_on_order() {
        assertThat(OrderedPair.of(1, 2).hashCode(), is(not(OrderedPair.of(2, 1).hashCode())));
    }

    @Test
    public void ordered_pair_should_not_be_equal_depending_on_order() {
        assertThat(OrderedPair.of(1, 2), is(not(OrderedPair.of(2, 1))));
    }

    @Test
    public void unordered_pair_should_have_same_hash_code_depending_on_order() {
        assertThat(UnorderedPair.of(1, 2).hashCode(), is(UnorderedPair.of(2, 1).hashCode()));
    }

    @Test
    public void unordered_pair_should_be_equal_depending_on_order() {
        assertThat(UnorderedPair.of(1, 2), is(UnorderedPair.of(2, 1)));
    }
}