package ophelia.generator.constructor;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import ophelia.generator.CodeWrapperWithImports;

/**
 * @author steven
 */
public interface ConstructorWrapper extends CodeWrapperWithImports<ConstructorDeclaration> {

	void setClass(String className);
}
