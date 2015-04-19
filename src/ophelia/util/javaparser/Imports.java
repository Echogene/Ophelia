package ophelia.util.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static ophelia.util.javaparser.Imports.Staticness.NON_STATIC;
import static ophelia.util.javaparser.Imports.Staticness.STATIC;

/**
 * @author Steven Weston
 */
public class Imports {

	private final Map<Staticness, List<ImportDeclaration>> staticGrouping;

	public enum Staticness {
		STATIC, NON_STATIC
	}

	public Imports(CompilationUnit cu) {

		List<ImportDeclaration> imports = cu.getImports();
		staticGrouping = imports.stream().collect(groupingBy(decl -> decl.isStatic() ? STATIC : NON_STATIC));
	}
}
