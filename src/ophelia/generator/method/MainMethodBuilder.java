package ophelia.generator.method;

import ophelia.map.UnmodifiableMap;

public interface MainMethodBuilder {

	MainMethodBuilder withParameters(UnmodifiableMap<Class<?>, String> parameterNames);

	MethodWrapper build();
}
