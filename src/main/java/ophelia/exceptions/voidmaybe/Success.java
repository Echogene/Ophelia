package ophelia.exceptions.voidmaybe;

import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Weston
 */
class Success implements VoidMaybe {
	@Nullable
	@Override
	public Exception getException() {
		return null;
	}

	@Override
	public void throwOnFailure() throws Exception {}
}
