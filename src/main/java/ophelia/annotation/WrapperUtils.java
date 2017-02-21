package ophelia.annotation;

import ophelia.util.function.FunctionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * A collection of methods to be used by classes annotated by {@link Wrapper}.
 * @author Steven Weston
 */
public class WrapperUtils {

	/**
	 * Convert a set of type {@code V} to one of type {@code W} using the given function.  The given function should
	 * be a constructor for a class {@code W} annotated with {@link Wrapper} for {@code V}.
	 */
	@NotNull
	public static <V, W> Set<W> wrap(@NotNull Function<V, W> wrapper, @NotNull Set<V> wrappee) {
		return FunctionUtils.image(wrappee, wrapper);
	}

	/**
	 * Convert a list of type {@code V} to one of type {@code W} using the given function.  The given function should
	 * be a constructor for a class {@code W} annotated with {@link Wrapper} for {@code V}.
	 */
	@NotNull
	public static <V, W> List<W> wrap(@NotNull Function<V, W> wrapper, @NotNull List<V> wrappee) {
		return FunctionUtils.image(wrappee, wrapper);
	}
}
