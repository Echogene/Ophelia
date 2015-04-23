package ophelia.util.javaparser;

import ophelia.util.CollectionUtils;
import ophelia.util.function.ExceptionalFunction;
import ophelia.util.javaparser.OtherTestClass.StaticNestedClass;
import ophelia.util.javaparser.OtherTestClass.StaticNestedClass.StaticDoubleNestedClass;

/**
 * @author Steven Weston
 */
class TestClass {

	private TestClass() {}

	public void test() {}

	public void test(String lol) {}

	public void test(boolean lol) {}
	public void test(char lol) {}
	public void test(byte lol) {}
	public void test(short lol) {}
	public void test(int lol) {}
	public void test(long lol) {}
	public void test(float lol) {}
	public void test(double lol) {}

	public void test(Boolean lol) {}
	public void test(Character lol) {}
	public void test(Byte lol) {}
	public void test(Short lol) {}
	public void test(Integer lol) {}
	public void test(Long lol) {}
	public void test(Float lol) {}
	public void test(Double lol) {}

	public void test(TestClass lol) {}
	public void test(JavaParserReflector lol) {}

	public void test(CollectionUtils lol) {}

	public void test(ExceptionalFunction lol) {}

	public void test(StaticNestedClass lol) {}
	public void test(StaticDoubleNestedClass lol) {}

	public void test(TestEnum lol) {}

	public void test(ophelia.util.TreeUtils lol) {}

	// todo: test varargs

	// todo: test with annotated parameters

	// todo: test with multiple parameters
}
