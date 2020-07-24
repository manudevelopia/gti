package info.developia.gti;

import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;

public class Injectable {
    public static Set<Class<?>> getInjectables(String packageName) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(Injection.class);
    }

    public static Set<Class<?>> getNoParamsConstructor(Set<Class<?>> injectables) {
        return injectables.stream().filter(Injectable::hasConstructorParams).collect(Collectors.toSet());
    }

    private static boolean hasConstructorParams(Class<?> clazz) {
        // TODO: getConstructors or getDeclaredConstructors? any constructor? check first? check all? do it as convention?
        return clazz.getConstructors().length == 1 && clazz.getConstructors()[0].getParameterCount() == 0;
    }
}
