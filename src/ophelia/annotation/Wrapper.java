package ophelia.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Steven Weston
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Wrapper {

	Class value();
}
