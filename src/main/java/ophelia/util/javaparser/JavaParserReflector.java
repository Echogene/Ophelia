package ophelia.util.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.PrimitiveType.Primitive;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import ophelia.exceptions.maybe.Maybe;
import ophelia.function.ExceptionalFunction;
import ophelia.map.UnmodifiableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static com.codepoetics.protonpack.StreamUtils.takeWhile;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Boolean;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Byte;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.*;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Double;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Float;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Long;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.Short;
import static java.util.stream.Stream.iterate;
import static ophelia.exceptions.maybe.Maybe.*;
import static ophelia.exceptions.maybe.MaybeCollectors.toListOfSuccesses;
import static ophelia.exceptions.maybe.MaybeCollectors.toUniqueSuccess;
import static ophelia.util.ListUtils.first;
import static ophelia.util.MapUtils.$;
import static ophelia.util.MapUtils.map;
import static ophelia.util.StringUtils.replaceLast;

/**
 * @author Steven Weston
 */
public class JavaParserReflector {

	private static final GenericVisitorAdapter<PackageDeclaration, Object> GET_PACKAGE_FROM_COMPILATION_UNIT
			= new GenericVisitorAdapter<PackageDeclaration, Object>() {
				@Override
				public PackageDeclaration visit(CompilationUnit n, Object arg) {
					return n.getPackage();
				}
			};

	private static final UnmodifiableMap<Primitive, Class<?>> primitiveClasses = map(
			$(Boolean, boolean.class),
			$(Char, char.class),
			$(Byte, byte.class),
			$(Short, short.class),
			$(Int, int.class),
			$(Long, long.class),
			$(Float, float.class),
			$(Double, double.class)
	);

	private static final GenericVisitorAdapter<Maybe<Class<?>>, CompilationUnit> TYPE_GETTER
			= new GenericVisitorAdapter<Maybe<Class<?>>, CompilationUnit>() {
				@Override
				public Maybe<Class<?>> visit(ReferenceType n, CompilationUnit cu) {
					Maybe<Class<?>> baseClass = n.getType().accept(CLASS_GETTER, cu);
					for (int i = 0; i < n.getArrayCount(); i++) {
						baseClass = baseClass.map(JavaParserReflector::getArrayClass);
					}
					return baseClass;
				}

				@Override
				public Maybe<Class<?>> visit(PrimitiveType n, CompilationUnit cu) {
					final Class<?> clazz = primitiveClasses.get(n.getType());
					return notNull(clazz);
				}
	};

	private static final GenericVisitorAdapter<Maybe<Class<?>>, CompilationUnit> CLASS_GETTER
			= new GenericVisitorAdapter<Maybe<Class<?>>, CompilationUnit>() {
		@Override
		public Maybe<Class<?>> visit(ClassOrInterfaceType n, CompilationUnit cu) {

			ClassOrInterfaceType scope = n.getScope();
			String name = n.getName();
			String className = scope == null ? name : scope + "." + name;
			return maybe(() -> tryToFindClass(className, cu));
		}

		@Override
		public Maybe<Class<?>> visit(PrimitiveType n, CompilationUnit cu) {
			final Class<?> clazz = primitiveClasses.get(n.getType());
			return notNull(clazz);
		}
	};

	private static final GenericVisitorAdapter<Maybe<Class<?>>, CompilationUnit> VARARGS_TYPE_GETTER
			= new GenericVisitorAdapter<Maybe<Class<?>>, CompilationUnit>() {
				@Override
				public Maybe<Class<?>> visit(ReferenceType n, CompilationUnit cu) {

					Maybe<Class<?>> baseClass = n.getType().accept(CLASS_GETTER, cu);
					return baseClass.map(JavaParserReflector::getArrayClass);
				}

				@Override
				public Maybe<Class<?>> visit(PrimitiveType n, CompilationUnit cu) {
					final Class<?> clazz = primitiveClasses.get(n.getType());
					return notNull(getArrayClass(clazz));
				}
	};

	private static final Function<String, Maybe<Class<?>>> MAYBE_CLASS = wrap(
			(ExceptionalFunction<String, Class<?>, Exception>) Class::forName
	);

	private static Class<?> getArrayClass(Class<?> c) {

		return Array.newInstance(c, 0).getClass();
	}

	@NotNull
	public static Class<?> tryToFindClass(String className, CompilationUnit cu) throws ClassNotFoundException {

		return Maybe.<Class<?>>maybe(() -> Class.forName(className))
				.returnOnSuccess()
				.tryAgain(() -> Class.forName("java.lang." + className))
				.tryAgain(() -> Class.forName(cu.getPackage().getName().toStringWithoutComments() + "." + className))
				.tryAgain(
						() -> {
							List<ImportDeclaration> imports = cu.getImports();

							List<? extends Class<?>> classesFromImports = imports.stream()
									.filter(decl -> importRefersToClassName(decl, className))
									.map(wrap(JavaParserReflector::getClassForImport))
									.collect(toListOfSuccesses());

							if (!classesFromImports.isEmpty()) {
								return first(classesFromImports);
							} else {
								return imports.stream()
										.filter(ImportDeclaration::isAsterisk)
										.map(ImportDeclaration::getName)
										.map(NameExpr::toStringWithoutComments)
										.map(name -> name + "." + className)
										.map(MAYBE_CLASS)
										.collect(toUniqueSuccess())
										.returnOnSuccess()
										.throwMappedFailure((e) -> new ClassNotFoundException(className, e));
							}
						}
				)
				.throwInstead(() -> new ClassNotFoundException(className));
	}

	private static boolean importRefersToClassName(ImportDeclaration declaration, String className) {
		return declaration.getName().getName().equals(className);
	}

	private static Class<?> getClassForImport(ImportDeclaration declaration) throws ClassNotFoundException {

		NameExpr declarationName = declaration.getName();
		String qualifiedClassName = declarationName.toStringWithoutComments();

		return getClassForImportName(qualifiedClassName);
	}

	private static Class<?> getClassForImportName(String qualifiedClassName) throws ClassNotFoundException {

		// Try getting the class until the arbitrarily nested inner class is found.
		Stream<String> stringStream = takeWhile(
				iterate(qualifiedClassName, name -> replaceLast(name, ".", "$")),
				name -> name.contains(".")
		);
		return stringStream.map(MAYBE_CLASS)
				.collect(toUniqueSuccess())
				.returnOnSuccess()
				.throwMappedFailure((e) -> new ClassNotFoundException(qualifiedClassName, e));
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

	/**
	 * Get a method from a class given a method declaration.
	 * @param declaration the declaration of the method we want to find reflectively
	 * @param clazz the class that owns the method
	 * @return the method represented by the given method declaration
	 * @throws NoSuchMethodException
	 */
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
					.map(parameter -> getClassForParameter(parameter, cu))
					.toArray((IntFunction<Class<?>[]>) Class[]::new);

			return clazz.getMethod(declaration.getName(), parameterClasses);
		}
	}

	@Nullable
	public static Class<?> getClassForParameter(Parameter parameter, CompilationUnit cu) {

		Type type = parameter.getType();
		if (parameter.isVarArgs()) {
			return type.accept(VARARGS_TYPE_GETTER, cu).returnOnSuccess().nullOnFailure();
		} else {
			return type.accept(TYPE_GETTER, cu).returnOnSuccess().nullOnFailure();
		}
	}
}
