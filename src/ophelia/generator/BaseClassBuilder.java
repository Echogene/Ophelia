package ophelia.generator;

import com.github.javaparser.ASTHelper;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import ophelia.generator.method.MethodWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Modifier.PUBLIC;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.singletonList;

/**
 * @author Steven Weston
 */
class BaseClassBuilder implements MainClassBuilder {

	private final PackageDeclaration packageDeclaration;

	private final List<ImportDeclaration> imports = new ArrayList<>();
	private final List<ClassOrInterfaceType> extensions = new ArrayList<>();
	private final List<ClassOrInterfaceType> implementations = new ArrayList<>();
	private final String className;
	private final List<MethodDeclaration> methods = new ArrayList<>();

	public BaseClassBuilder(String packageName, String className) {
		this.className = className;

		packageDeclaration = new PackageDeclaration(new NameExpr(packageName));
	}

	@Override
	public MainClassBuilder withImport(String canonicalClassName) {
		imports.add(new ImportDeclaration(new NameExpr(canonicalClassName), false, false));
		return this;
	}

	@NotNull
	@Override
	public MainClassBuilder withExtends(@NotNull Class<?> clazz) {
		withImport(clazz.getCanonicalName());
		extensions.add(new ClassOrInterfaceType(clazz.getSimpleName()));
		return this;
	}

	@NotNull
	@Override
	public MainClassBuilder withImplements(@NotNull Class<?> clazz) {
		withImport(clazz.getCanonicalName());
		implementations.add(new ClassOrInterfaceType(clazz.getSimpleName()));
		return this;
	}

	@NotNull
	@Override
	public MainClassBuilder withMethod(@NotNull MethodWrapper method) {
		methods.add(method.getNode());
		withImports(method.getImports().stream());
		return this;
	}

	@Override
	public void writeToFile(File file) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF_8))) {

			ClassOrInterfaceDeclaration typeDeclaration = new ClassOrInterfaceDeclaration(
					PUBLIC,
					null,
					false,
					className,
					null,
					extensions.isEmpty() ? null : extensions,
					implementations.isEmpty() ? null : implementations,
					new ArrayList<>()
			);

			methods.forEach(method -> ASTHelper.addMember(typeDeclaration, method));

			CompilationUnit cu = new CompilationUnit(
					packageDeclaration,
					imports,
					singletonList(typeDeclaration)
			);
			writer.write(cu.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
