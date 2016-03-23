package ophelia.exceptions.voidmaybe;

import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Weston
 */
class Failure implements VoidMaybe {

	private final Exception exception;

	Failure(Exception exception) {
		this.exception = exception;
	}

	@Nullable
	@Override
	public Exception getException() {
		return exception;
	}

	@Override
	public void throwOnFailure() throws Exception {
		throw exception;
	}
}
