package ophelia.exceptions.voidmaybe;

import ophelia.collections.list.UnmodifiableList;
import ophelia.exceptions.CollectedException;

import java.util.*;
import java.util.stream.Collector;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;
import static ophelia.exceptions.voidmaybe.Success.SUCCESS;
import static ophelia.util.MapUtils.updateSetBasedMap;

public interface VoidMaybeCollectors {
	static Collector<VoidMaybe, Map<Boolean, Set<VoidMaybe>>, VoidMaybe> toUniqueSuccess() {
		return Collector.of(
				HashMap::new,
				(map, m) -> updateSetBasedMap(map, m.isSuccess(), m),
				(left, right) -> {
					left.putAll(right);
					return left;
				},
				map -> {
					Set<VoidMaybe> successes = map.getOrDefault(true, emptySet());
					Set<VoidMaybe> failures = map.getOrDefault(false, emptySet());
					if (successes.isEmpty()) {
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
					return SUCCESS;
				}
		);
	}
}
