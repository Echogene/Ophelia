package ophelia.generator;

/**
 * @author Steven Weston
 */
public interface MainClassBuilder {

	MainClassBuilder withImport(String classToImport);

	void generate();
}
