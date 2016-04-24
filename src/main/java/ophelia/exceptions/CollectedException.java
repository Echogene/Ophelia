package ophelia.exceptions;

import ophelia.collections.list.UnmodifiableList;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Steven Weston
 */
public class CollectedException extends Exception {

	protected final List<Exception> list;

	public CollectedException(List<Exception> exceptions) {
		list = exceptions;
	}

	@Override
	public String getMessage() {
		return list.stream()
				.map(Exception::getMessage)
				.collect(Collectors.joining("\n"));
	}

	@Override
	public String getLocalizedMessage() {

		return list.stream()
				.map(Exception::getLocalizedMessage)
				.collect(Collectors.joining("\n"));
	}

	@Override
	public String toString() {

		return list.stream()
				.map(Exception::toString)
				.collect(Collectors.joining("\n"));
	}

	@Override
	public void printStackTrace() {
		list.stream().forEach(Exception::printStackTrace);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		list.stream().forEach(e -> e.printStackTrace(s));
	}

	@Override
	public void printStackTrace(PrintStream s) {
		list.stream().forEach(e -> e.printStackTrace(s));
	}

	public UnmodifiableList<Exception> getExceptions() {
		return new UnmodifiableList<>(list);
	}

	public static UnmodifiableList<Exception> flatten(Exception exception) {
		return new UnmodifiableList<>(innerFlatten(exception));
	}

	private static List<Exception> innerFlatten(Exception exception) {
		if (exception instanceof CollectedException) {
			CollectedException collectedException = (CollectedException) exception;
			return collectedException.getExceptions().stream()
					.map(CollectedException::innerFlatten)
					.flatMap(List::stream)
					.collect(Collectors.toList());
		} else {
			return Collections.singletonList(exception);
		}
	}
}
