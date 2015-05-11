package ophelia.generator;

/**
 * @author Steven Weston
 */
public class ClassBuilder implements ClassBuilderNeedingPackageName {

	private final String className;

	public ClassBuilder(String className) {
		this.className = className;
	}

	@Override
	public MainClassBuilder withPackage(String packageName) {
		return new BaseClassBuilder(packageName, className);
	}
}
