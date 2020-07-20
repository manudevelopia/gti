package info.developia.gti;

import org.reflections.Reflections;

import java.util.Set;

public class Injectable {
    public static Set<Class<?>> getInjectables(Class<?> clazz) {
        Reflections reflections = new Reflections(clazz.getPackageName());
        return reflections.getTypesAnnotatedWith(Injection.class);
    }

    public static Set<Class<?>> getInjectables(String packageName) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(Injection.class);
    }
}
