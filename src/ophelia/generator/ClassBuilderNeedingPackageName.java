package ophelia.generator;

/**
 * @author Steven Weston
 */
public interface ClassBuilderNeedingPackageName {

	ClassBuilderNeedingClassName withPackage(String packageName);
}
