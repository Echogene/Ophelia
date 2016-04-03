package ophelia.collections.matchers;

import ophelia.collections.IntegerFiniteCollection;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Like {@link org.hamcrest.collection.IsCollectionWithSize}, but for Ophelia collections (specifically
 * {@link IntegerFiniteCollection}s).
 * @see org.hamcrest.collection.IsCollectionWithSize
 * @param <E> the type contained within the matched collection
 */
public class IsCollectionWithSize<E> extends FeatureMatcher<IntegerFiniteCollection<? extends E, ?>, Integer> {

	public IsCollectionWithSize(Matcher<? super Integer> sizeMatcher) {
		super(sizeMatcher, "a collection with size", "collection size");
	}

	@Override
	protected Integer featureValueOf(IntegerFiniteCollection<? extends E, ?> actual) {
		return actual.size();
	}

	@Factory
	public static <E> Matcher<IntegerFiniteCollection<? extends E, ?>> hasSize(Matcher<? super Integer> sizeMatcher) {
		return new IsCollectionWithSize<>(sizeMatcher);
	}

	@Factory
	public static <E> Matcher<IntegerFiniteCollection<? extends E, ?>> hasSize(int size) {
		return IsCollectionWithSize.hasSize(equalTo(size));
	}
}
