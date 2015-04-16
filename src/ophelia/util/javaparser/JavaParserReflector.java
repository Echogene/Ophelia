package ophelia.util.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.PrimitiveType.Primitive;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import ophelia.exceptions.maybe.Maybe;
import ophelia.util.MapUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import static com.github.javaparser.ast.type.PrimitiveType.Primitive.*;
import static ophelia.exceptions.maybe.Maybe.maybe;
import static ophelia.util.MapUtils.$;
import static ophelia.util.MapUtils.map;

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

	private static final Map<Primitive, Class<?>> primitiveClasses = map(
			$(Boolean, boolean.class),
			$(Char, char.class),
			$(Byte, byte.class),
			$(Short, short.class),
			$(Int, int.class),
			$(Long, long.class),
			$(Float, float.class),
			$(Double, double.class)
	);

	private static final GenericVisitorAdapter<Maybe<Class<?>, ClassNotFoundException>, Object> TYPE_GETTER
			= new GenericVisitorAdapter<Maybe<Class<?>, ClassNotFoundException>, Object>() {
				@Override
				public Maybe<Class<?>, ClassNotFoundException> visit(ReferenceType n, Object arg) {
					return n.getType().accept(CLASS_GETTER, null);
				}

				@Override
				public Maybe<Class<?>, ClassNotFoundException> visit(PrimitiveType n, Object arg) {
					final Class<?> clazz = primitiveClasses.get(n.getType());
					return maybe(clazz);
				}
	};

	private static final GenericVisitorAdapter<Maybe<Class<?>, ClassNotFoundException>, Object> CLASS_GETTER
			= new GenericVisitorAdapter<Maybe<Class<?>, ClassNotFoundException>, Object>() {
				@Override
				public Maybe<Class<?>, ClassNotFoundException> visit(ClassOrInterfaceType n, Object arg) {
					return maybe(() -> Class.forName("java.lang." + n.getName()));
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
			Class<?>[] parameterClasses = parameters.stream()
					.map(Parameter::getType)
					.map(JavaParserReflector::getClassForType)
					.toArray((IntFunction<Class<?>[]>) Class[]::new);

			return clazz.getMethod(declaration.getName(), parameterClasses);
		}
	}

	@Nullable
	public static Class<?> getClassForType(Type type) {
		return type.accept(TYPE_GETTER, null).returnOnSuccess().nullOnFailure();
	}
}
