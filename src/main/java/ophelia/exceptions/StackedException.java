package ophelia.exceptions;

import java.util.ArrayList;

/**
 * @author Steven Weston
 */
public class StackedException extends CollectedException {

	public StackedException(Exception e) {
		super(new ArrayList<>());
		addException(e);
	}

	public void addException(Exception e) {
		list.add(e);
	}
}
