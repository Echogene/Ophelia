package ophelia.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class StackedException extends Exception {

	private final List<Exception> list;

	public StackedException(Exception e) {
		list = new ArrayList<>();
		addException(e);
	}

	public void addException(Exception e) {
		list.add(e);
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

	public List<Exception> getExceptions() {
		return list;
	}
}
