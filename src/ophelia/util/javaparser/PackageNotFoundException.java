package ophelia.util.javaparser;

/**
 * @author Steven Weston
 */
public class PackageNotFoundException extends ReflectiveOperationException {

	public PackageNotFoundException(String message) {
		super(message);
	}
}
