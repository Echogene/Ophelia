package ophelia.builder;

/**
 * @author Steven Weston
 */
public interface VoidBuilder<B extends VoidBuilder<B>> extends SelfReturner<B> {

	void build();
}
