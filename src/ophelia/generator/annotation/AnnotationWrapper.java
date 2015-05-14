package ophelia.generator.annotation;

import com.github.javaparser.ast.expr.AnnotationExpr;
import ophelia.generator.CodeWrapperWithImports;
import ophelia.generator.NodeWrapper;

/**
 * @author Steven Weston
 */
public interface AnnotationWrapper
		extends CodeWrapperWithImports, NodeWrapper<AnnotationExpr> {

}
