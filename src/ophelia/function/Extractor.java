package ophelia.function;

/**
 * @author Steven Weston
 */
@FunctionalInterface
public interface Extractor<K, V> {

	V extract(K k) throws ExtractorException;
}
