package ophelia.util.javaparser;

import com.codepoetics.protonpack.collectors.CollectorUtils;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import static com.codepoetics.protonpack.StreamUtils.reject;
import static com.codepoetics.protonpack.StreamUtils.takeWhile;
import static com.codepoetics.protonpack.collectors.CollectorUtils.unique;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Boolean;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Byte;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.*;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Double;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Float;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Long;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Short;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.iterate;
import static ophelia.exceptions.maybe.Maybe.*;
import static ophelia.util.CollectionUtils.first;
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

	private static final GenericVisitorAdapter<Maybe<Class<?>, ClassNotFoundException>, CompilationUnit> TYPE_GETTER
			= new GenericVisitorAdapter<Maybe<Class<?>, ClassNotFoundException>, CompilationUnit>() {
				@Override
				public Maybe<Class<?>, ClassNotFoundException> visit(ReferenceType n, CompilationUnit cu) {
					return n.getType().accept(CLASS_GETTER, cu);
				}

				@Override
				public Maybe<Class<?>, ClassNotFoundException> visit(PrimitiveType n, CompilationUnit cu) {
					final Class<?> clazz = primitiveClasses.get(n.getType());
					return maybe(clazz);
				}
	};

	private static final GenericVisitorAdapter<Maybe<Class<?>, ClassNotFoundException>, CompilationUnit> CLASS_GETTER
			= new GenericVisitorAdapter<Maybe<Class<?>, ClassNotFoundException>, CompilationUnit>() {
				@Override
				public Maybe<Class<?>, ClassNotFoundException> visit(ClassOrInterfaceType n, CompilationUnit cu) {
					return maybe(() -> tryToFindClass(n, cu));
				}
			};

	@NotNull
	private static Class<?> tryToFindClass(ClassOrInterfaceType n, CompilationUnit cu) throws ClassNotFoundException {
		try {
			return Class.forName("java.lang." + n.getName());

		} catch (ClassNotFoundException e) {
			try {
				return Class.forName(cu.getPackage().getName().toStringWithoutComments() + "." + n.getName());

			} catch (ClassNotFoundException f) {
				List<ImportDeclaration> nonStaticImports
						= reject(cu.getImports().stream(), ImportDeclaration::isStatic).collect(toList());

				List<? extends Class<?>> classesFromImports = filterPassingValues(
						nonStaticImports.stream().filter(decl -> hasSameName(n, decl)),
						decl -> Class.forName(decl.getName().toStringWithoutComments())
				).collect(toList());

				if (!classesFromImports.isEmpty()) {
					return first(classesFromImports);
				} else {
					return filterPassingValues(
							nonStaticImports.stream().filter(ImportDeclaration::isAsterisk),
							decl -> Class.forName(decl.getName().toStringWithoutComments() + "." + n.getName())
					).collect(unique())
							.orElseThrow(() -> new ClassNotFoundException(n.getName()));
				}
			}
		}
	}

	private static boolean hasSameName(ClassOrInterfaceType type, ImportDeclaration declaration) {
		return declaration.getName().getName().equals(type.getName());
	}

	@NotNull
	public static Class<?> findClass(ClassOrInterfaceDeclaration declaration)
			throws PackageNotFoundException, ClassNotFoundException {

		String className = declaration.getName();

		PackageDeclaration packageDeclaration
				= notNull(declaration.getParentNode().accept(GET_PACKAGE_FROM_COMPILATION_UNIT, null))
						.returnOnSuccess()
						.throwMappedFailure(e -> new PackageNotFoundException(className));

		String packageName = packageDeclaration.getName().toStringWithoutComments();

		return Class.forName(packageName + "." + className);
	}

	@NotNull
	public static Method findMethod(MethodDeclaration declaration, Class<?> clazz) throws NoSuchMethodException {

		List<Parameter> parameters = declaration.getParameters();
		if (parameters == null || parameters.isEmpty()) {
			return clazz.getMethod(declaration.getName());
		} else {
			CompilationUnit cu = takeWhile(iterate(declaration, Node::getParentNode), node -> node != null)
					.filter(CompilationUnit.class::isInstance)
					.map(CompilationUnit.class::cast)
					.findFirst().get();

			Class<?>[] parameterClasses = parameters.stream()
					.map(Parameter::getType)
					.map(type -> getClassForType(type, cu))
					.toArray((IntFunction<Class<?>[]>) Class[]::new);

			return clazz.getMethod(declaration.getName(), parameterClasses);
		}
	}

	@Nullable
	public static Class<?> getClassForType(Type type, CompilationUnit cu) {
		return type.accept(TYPE_GETTER, cu).returnOnSuccess().nullOnFailure();
	}
}
