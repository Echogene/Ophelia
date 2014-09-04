package util;

/**
 * @author Steven Weston
 */
public interface Extractor<K, V> {

	V extract(K k) throws ExtractorException;
}
