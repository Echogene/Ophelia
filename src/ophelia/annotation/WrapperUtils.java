package ophelia.annotation;

import ophelia.util.FunctionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
public class WrapperUtils {

	@NotNull
	public static <V, W> Set<W> wrap(@NotNull Function<V, W> wrapper, @NotNull Set<V> wrappee) {
		return FunctionUtils.image(wrappee, wrapper);
	}

	@NotNull
	public static <V, W> List<W> wrap(@NotNull Function<V, W> wrapper, @NotNull List<V> wrappee) {
		return FunctionUtils.image(wrappee, wrapper);
	}
}
