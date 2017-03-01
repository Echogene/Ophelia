package ophelia.util.function;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public interface PredicateUtils {

	static <T> Predicate<? super T> not(@NotNull Predicate<? super T> predicate) {
		return predicate.negate();
	}

	@NotNull
	@Contract(pure = true)
	static <T> Predicate<? super T> notNull() {
		return Objects::nonNull;
	}

	@NotNull
	@Contract(pure = true)
	static <T> Predicate<? super T> isNull() {
		return Objects::isNull;
	}

	@NotNull
	static <S, T> Predicate<? super T> notNull(@NotNull Function<? super T, ? extends S> function) {
		return t -> Objects.nonNull(function.apply(t));
	}

	@NotNull
	static <S, T> Predicate<? super T> isNull(@NotNull Function<? super T, ? extends S> function) {
		return t -> Objects.isNull(function.apply(t));
	}

	@NotNull
	@Contract(pure = true)
	static <T> Predicate<? super T> equals(@Nullable T equalTo) {
		if (equalTo == null) {
			return isNull();
		} else {
			return equalTo::equals;
		}
	}

	@NotNull
	@Contract(pure = true)
	static <T> Predicate<? super T> notEquals(@Nullable T equalTo) {
		if (equalTo == null) {
			return notNull();
		} else {
			return t -> !equalTo.equals(t);
		}
	}
}
