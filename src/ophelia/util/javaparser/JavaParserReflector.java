package ophelia.util.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Steven Weston
 */
public class JavaParserReflector {

	public static final GenericVisitorAdapter<PackageDeclaration, Object> GET_PACKAGE_FROM_COMPILATION_UNIT
			= new GenericVisitorAdapter<PackageDeclaration, Object>() {
				@Override
				public PackageDeclaration visit(CompilationUnit n, Object arg) {
					return n.getPackage();
				}
			};

	private static final GenericVisitorAdapter<Class<?>, Object> TYPE_GETTER
			= new GenericVisitorAdapter<Class<?>, Object>() {
				@Override
				public Class<?> visit(ReferenceType n, Object arg) {
					return n.getType().accept(CLASS_GETTER, null);
				}

				@Override
				public Class<?> visit(PrimitiveType n, Object arg) {
					switch (n.getType()) {
						case Boolean:
							return boolean.class;
						case Char:
							return char.class;
						case Byte:
							return byte.class;
						case Short:
							return short.class;
						case Int:
							return int.class;
						case Long:
							return long.class;
						case Float:
							return float.class;
						case Double:
							return double.class;
						default:
							return null;
					}
				}
	};

	private static final GenericVisitorAdapter<Class<?>, Object> CLASS_GETTER
			= new GenericVisitorAdapter<Class<?>, Object>() {
				@Override
				public Class<?> visit(ClassOrInterfaceType n, Object arg) {

					try {
						return Class.forName("java.lang." + n.getName());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						throw new NotImplementedException();
					}
				}
			};

	@NotNull
	public static Class<?> findClass(ClassOrInterfaceDeclaration declaration)
			throws PackageNotFoundException, ClassNotFoundException {

		String className = declaration.getName();

		PackageDeclaration packageDeclaration = declaration.getParentNode().accept(
				GET_PACKAGE_FROM_COMPILATION_UNIT, null
		);
		if (packageDeclaration == null) {
			throw new PackageNotFoundException(className);
		}
		String packageName = packageDeclaration.getName().toStringWithoutComments();

		return Class.forName(packageName + "." + className);
	}

	@NotNull
	public static Method findMethod(MethodDeclaration declaration, Class<?> clazz) throws NoSuchMethodException {

		List<Parameter> parameters = declaration.getParameters();
		if (parameters == null || parameters.isEmpty()) {
			return clazz.getMethod(declaration.getName());
		} else {
			List<Class<?>> parameterClasses = parameters.stream()
					.map(Parameter::getType)
					.map(JavaParserReflector::getClassForType)
					.collect(Collectors.toList());

			Class<?>[] parameterTypes = parameterClasses.toArray(new Class<?>[parameterClasses.size()]);
			return clazz.getMethod(declaration.getName(), parameterTypes);
		}
	}

	@Nullable
	public static Class<?> getClassForType(Type type) {
		return type.accept(TYPE_GETTER, null);
	}
}
