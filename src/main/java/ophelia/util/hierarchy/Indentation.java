package ophelia.util.hierarchy;

import org.jetbrains.annotations.NotNull;

import static java.lang.Math.min;

public interface Indentation {
	@NotNull
	String getUnindentedString(@NotNull String indented);

	int getLevelOfIndentation(@NotNull String indented);

	@NotNull
	static Indentation ofString(@NotNull String indentation) {
		if (indentation.isEmpty()) {
			return new Indentation() {
				@Override
				@NotNull
				public String getUnindentedString(@NotNull String indented) {
					return indented;
				}

				@Override
				public int getLevelOfIndentation(@NotNull String indented) {
					return 0;
				}
			};
		}
		return new Indentation() {
			@Override
			@NotNull
			public String getUnindentedString(@NotNull String indented) {
				int indentationLength = indentation.length();
				int indentedLength = indented.length();
				for (int i = 0; i < indentedLength; i += indentationLength) {
					String substring = indented.substring(i, min(i + indentationLength, indentedLength));
					if (!substring.equals(indentation)) {
						return indented.substring(i);
					}
				}
				return "";
			}

			@Override
			public int getLevelOfIndentation(@NotNull String indented) {
				int indentationLength = indentation.length();
				int indentedLength = indented.length();
				for (int i = 0; i < indentedLength; i += indentationLength) {
					String substring = indented.substring(i, min(i + indentationLength, indentedLength));
					if (!substring.equals(indentation)) {
						return i / indentationLength;
					}
				}
				return indentedLength / indentationLength;
			}
		};
	}
}
