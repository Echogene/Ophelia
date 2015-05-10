package ophelia.generator;

import java.io.File;

/**
 * @author Steven Weston
 */
public class ClassBuilder implements ClassBuilderNeedingPackageName {

	private final File outputFile;

	public ClassBuilder(File outputFile) {
		this.outputFile = outputFile;
	}

	@Override
	public ClassBuilderNeedingClassName withPackage(String packageName) {
		return new ClassBuilderWithPackage(packageName);
	}

	private class ClassBuilderWithPackage implements ClassBuilderNeedingClassName {

		private final String packageName;

		private ClassBuilderWithPackage(String packageName) {
			this.packageName = packageName;
		}

		@Override
		public MainClassBuilder withClassName(String className) {
			return new BaseClassBuilder(outputFile, packageName, className);
		}
	}
}
