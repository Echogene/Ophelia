package ophelia.generator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Modifier.PUBLIC;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * @author Steven Weston
 */
class BaseClassBuilder implements MainClassBuilder {

	private final File outputFile;
	private final PackageDeclaration packageDeclaration;

	private final List<ImportDeclaration> imports = new ArrayList<>();
	private final List<ClassOrInterfaceType> extensions = new ArrayList<>();
	private final List<ClassOrInterfaceType> implementations = new ArrayList<>();
	private final String className;

	public BaseClassBuilder(File outputFile, String packageName, String className) {
		this.outputFile = outputFile;
		this.className = className;

		packageDeclaration = new PackageDeclaration(new NameExpr(packageName));
	}

	@Override
	public MainClassBuilder withImport(String classToImport) {
		imports.add(new ImportDeclaration(new NameExpr(classToImport), false, false));
		return this;
	}

	@Override
	public MainClassBuilder withExtends(Class<?> clazz) {
		withImport(clazz.getCanonicalName());
		extensions.add(new ClassOrInterfaceType(clazz.getSimpleName()));
		return this;
	}

	@Override
	public MainClassBuilder withImplements(Class<?> clazz) {
		withImport(clazz.getCanonicalName());
		implementations.add(new ClassOrInterfaceType(clazz.getSimpleName()));
		return this;
	}

	@Override
	public void generate() {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), UTF_8))) {

			ClassOrInterfaceDeclaration typeDeclaration = new ClassOrInterfaceDeclaration(
					PUBLIC,
					null,
					false,
					className,
					null,
					extensions.isEmpty() ? null : extensions,
					implementations.isEmpty() ? null : implementations,
					emptyList()
			);

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
