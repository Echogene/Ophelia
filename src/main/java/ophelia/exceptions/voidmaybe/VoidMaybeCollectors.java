package ophelia.exceptions.voidmaybe;

import ophelia.collections.list.UnmodifiableList;
import ophelia.exceptions.AmbiguityException;
import ophelia.exceptions.CollectedException;
import ophelia.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collector;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;
import static ophelia.exceptions.voidmaybe.Success.SUCCESS;
import static ophelia.util.MapUtils.updateListBasedMap;
import static ophelia.util.MapUtils.updateSetBasedMap;

public class VoidMaybeCollectors {

	/**
	 * @return a collector that collects to a successful {@link VoidMaybe} when exactly one of its collectees is
	 * successful, or to a failure otherwise.
	 */
	@NotNull
	public static Collector<VoidMaybe, Map<Boolean, List<VoidMaybe>>, VoidMaybe> successOnExactlyOneSuccess() {
		return Collector.of(
				HashMap::new,
				(map, m) -> updateListBasedMap(map, m.isSuccess(), m),
				CollectionUtils::mergeToLeft,
				map -> {
					List<VoidMaybe> successes = map.getOrDefault(true, emptyList());
					List<VoidMaybe> failures = map.getOrDefault(false, emptyList());
					if (successes.isEmpty()) {
						return collapseFailures(failures);

					} else if (successes.size() == 1) {
						return SUCCESS;

					} else {
						return VoidMaybe.failure(new AmbiguityException(MessageFormat.format(
								"Expected exactly 1 success, but got {0}.",
								successes.size()
						)));
					}
				}
		);
	}

	@NotNull
	private static VoidMaybe collapseFailures(@NotNull Collection<VoidMaybe> failures) {
		if (failures.isEmpty()) {
			return VoidMaybe.failure(new NoSuchElementException());

		} else if (failures.size() > 1) {
			return VoidMaybe.multipleFailures(
					failures.stream()
							.map(VoidMaybeHandler::getException)
							.map(Optional::get)
							.map(CollectedException::flatten)
							.flatMap(UnmodifiableList::stream)
							.collect(toList())
			);
		} else {
			return failures.iterator().next();
		}
	}

	/**
	 * @return a collector that collects to a successful {@link VoidMaybe} when at least one of its collectees is
	 * successful, or to a failure if there are no successes.
	 */
	@NotNull
	public static Collector<VoidMaybe, Map<Boolean, Set<VoidMaybe>>, VoidMaybe> successOnAtLeastOneSuccess() {
		return Collector.of(
				HashMap::new,
				(map, m) -> updateSetBasedMap(map, m.isSuccess(), m),
				CollectionUtils::mergeToLeft,
				map -> {
					Set<VoidMaybe> successes = map.getOrDefault(true, emptySet());
					Set<VoidMaybe> failures = map.getOrDefault(false, emptySet());
					if (successes.isEmpty()) {
						return collapseFailures(failures);
					}
					return SUCCESS;
				}
		);
	}

	/**
	 * @return a collector that collects to a successful {@link VoidMaybe} when all of its collectees are successful,
	 * or to a failure if any of them are failures
	 */
	@NotNull
	public static Collector<VoidMaybe, Map<Boolean, Set<VoidMaybe>>, VoidMaybe> successOnAllSuccess() {
		return Collector.of(
				HashMap::new,
				(map, m) -> updateSetBasedMap(map, m.isSuccess(), m),
				CollectionUtils::mergeToLeft,
				map -> {
					Set<VoidMaybe> failures = map.getOrDefault(false, emptySet());
					if (failures.isEmpty()) {
						return SUCCESS;
					} else {
						return collapseFailures(failures);
					}
				}
		);
	}

	/**
	 * @return a collector that collects to a successful {@link VoidMaybe} when all (and at least one) of its
	 * collectees are successful, or to a failure if any of them are failures
	 */
	@NotNull
	public static Collector<VoidMaybe, Map<Boolean, Set<VoidMaybe>>, VoidMaybe> merge() {
		return Collector.of(
				HashMap::new,
				(map, m) -> updateSetBasedMap(map, m.isSuccess(), m),
				CollectionUtils::mergeToLeft,
				map -> {
					Set<VoidMaybe> successes = map.getOrDefault(true, emptySet());
					Set<VoidMaybe> failures = map.getOrDefault(false, emptySet());
					if (failures.isEmpty()) {
						if (successes.isEmpty()) {
							return VoidMaybe.failure(new NoSuchElementException());
						} else {
							return SUCCESS;
						}
					} else {
						return collapseFailures(failures);
					}
				}
		);
	}
}
