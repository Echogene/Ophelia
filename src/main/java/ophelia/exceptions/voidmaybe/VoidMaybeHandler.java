package ophelia.exceptions.voidmaybe;

import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Weston
 */
public interface VoidMaybeHandler {

	@Nullable
	Exception getException();

	void throwOnFailure() throws Exception;
}
