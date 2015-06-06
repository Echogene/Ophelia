package ophelia.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates that the annotated class is a wrapper for another class.  A wrapper has a single field whose type is equal
 * to the wrapped class.  It delegates all method calls to this field.
 * @author Steven Weston
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Wrapper {

	/**
	 * @return the class that is wrapped (the wrappee) by this wrapper
	 */
	@NotNull Class<?> value();
}
