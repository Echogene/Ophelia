package ophelia.generator.method;

import com.github.javaparser.ast.body.MethodDeclaration;
import ophelia.generator.CodeWrapperWithImports;
import ophelia.generator.NodeWrapper;

public interface MethodWrapper
		extends CodeWrapperWithImports, NodeWrapper<MethodDeclaration> {
}
