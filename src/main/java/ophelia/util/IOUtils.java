package ophelia.util;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Steven Weston
 */
public class IOUtils {

	/**
	 * Obtained from <a href=http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string>here</a>
	 */
	@NotNull
	public static String convertStreamToString(@NotNull InputStream is) {
		Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}
