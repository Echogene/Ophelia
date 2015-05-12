package ophelia.generator;

import ophelia.map.UnmodifiableMap;

public interface MainMethodBuilder {

	MainMethodBuilder withParameters(UnmodifiableMap<Class<?>, String> parameterNames);

	MethodWrapper build();
}
