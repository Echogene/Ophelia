package ophelia.util;

import java.io.InputStream;
import java.lang.String;import java.util.Scanner;

/**
 * @author Steven Weston
 */
public class IOUtils {

	/**
	 * Obtained from <a href=http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string>here</a>
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}
