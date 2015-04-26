package ophelia.util.javaparser;

import ophelia.util.CollectionUtils;
import ophelia.util.function.ExceptionalFunction;
import ophelia.util.javaparser.OtherTestClass.StaticNestedClass;
import ophelia.util.javaparser.OtherTestClass.StaticNestedClass.StaticDoubleNestedClass;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The worst class.
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

	public void test(String... lols) {}
	public void test(boolean... lols) {}
	public void test(char... lols) {}
	public void test(byte... lols) {}
	public void test(short... lols) {}

	public void test(Boolean[] lols) {}
	public void test(int[] lols) {}
	public void test(long[] lols) {}
	public void test(float[] lols) {}
	public void test(double[] lols) {}

	public void test(Boolean[][] lols) {}
	public void test(int[][] lols) {}
	public void test(long[][] lols) {}
	public void test(float[][] lols) {}
	public void test(double[][] lols) {}
	public void test(Boolean[][][][] lols) {}

	public void test(@NotNull Exception lol) {}

	public void test(String lol, String wut) {}
	public void test(Boolean lol, boolean wut) {}

	public void test(List<String> lols) {}
}
