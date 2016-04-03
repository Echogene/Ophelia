package ophelia.exceptions;

import ophelia.collections.iterator.BaseIterator;
import ophelia.collections.iterator.BaseListIterator;
import ophelia.collections.list.BaseList;
import ophelia.collections.list.StandardList;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

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
				.reduce("", (s, t) -> s + "\n" + t);
	}

	@Override
	public String getLocalizedMessage() {

		return list.stream()
				.map(Exception::getLocalizedMessage)
				.reduce("", (s, t) -> s + "\n" + t);
	}

	@Override
	public String toString() {

		return list.stream()
				.map(Exception::toString)
				.reduce("", (s, t) -> s + "\n" + t);
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

	public BaseList<Exception, ? extends BaseIterator<Exception>, ? extends BaseListIterator<Exception>> getExceptions() {
		return new StandardList<>(list);
	}
}
