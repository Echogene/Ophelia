package ophelia.builder;

/**
 * A {@code VoidBuilder} can return itself and build something remotely.
 * @param <B> the type of the class implementing this interface
 * @author Steven Weston
 */
public interface VoidBuilder<B extends VoidBuilder<B>> extends SelfReturner<B> {

	/**
	 * Do something using the configuration that has been passed into this {@code VoidBuilder}.
	 */
	void build();
}
