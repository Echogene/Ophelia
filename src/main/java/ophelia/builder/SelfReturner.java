package ophelia.builder;

import org.jetbrains.annotations.NotNull;

/**
 * Represents something that can return itself.  Used by Builders.
 * @author Steven Weston
 */
public interface SelfReturner<B> {

	/**
	 * Do nothing other than return this.
	 * @return this
	 */
	@NotNull B noöp();
}
