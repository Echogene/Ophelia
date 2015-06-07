package ophelia.util.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.text.MessageFormat.format;
import static ophelia.util.ListUtils.first;

/**
 * @author Steven Weston
 */
public class SourceFinder {

	/**
	 * Given a class, find the source file searching from the current directory.
	 */
	@NotNull
	public static File findSourceFile(Class<?> clazz) throws IOException {
		String currentDirectoryName = System.getProperty("user.dir");
		Path currentDirectory = Paths.get(currentDirectoryName);
		String fileName = clazz.getSimpleName() + ".java";

		final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + "**/" + fileName);

		final List<File> matchedPaths = new ArrayList<>();
		Files.walkFileTree(
				currentDirectory, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

						if (file.getFileName() != null && matcher.matches(file)) {
							matchedPaths.add(new File(file.toUri()));
						}
						return CONTINUE;
					}
				}
		);
		if (matchedPaths.size() == 1) {
			return first(matchedPaths);
		} else {
			throw new IOException(format("Cannot find unique file for class {0}", clazz.getSimpleName()));
		}
	}

	@NotNull
	public static CompilationUnit parseSource(File file) throws IOException, ParseException {
		try (FileInputStream fis = new FileInputStream(file)) {
			return JavaParser.parse(fis);
		}
	}
}
