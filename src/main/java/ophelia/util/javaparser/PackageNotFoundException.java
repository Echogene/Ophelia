package ophelia.util.javaparser;

import static java.text.MessageFormat.format;

/**
 * @author Steven Weston
 */
public class PackageNotFoundException extends ReflectiveOperationException {

	public PackageNotFoundException(String className) {
		super(format("Package not found for class {0}", className));
	}
}
