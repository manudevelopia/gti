package info.developia.gti;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Injectable {

    public static Set<Class<?>> getInjectables(String packageName) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(Injection.class);
    }

    public static Map<String, Object> initialize(Set<Class<?>> injectables) {
        Map<String, Object> instances = new HashMap<>();
        injectables.forEach(clazz -> {
            Object instance = getInstanceOf(clazz);
            instances.put(instance.getClass().getCanonicalName(), instance);
        });
        return instances;
    }

    private static Object getInstanceOf(Class<?> clazz) {
        Object[] args = getClassArgs(clazz);
        try {
            return getConstructor(clazz).newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new InjectionException("Cannot get an instance of the provided class '" + clazz.getCanonicalName() + "'");
        }
    }

    private static Constructor<?> getConstructor(Class<?> clazz) {
        // TODO: check  constructor
        return clazz.getConstructors()[0];
    }

    private static Object[] getClassArgs(Class<?> clazz) {
        Set<Class<?>> argumentClasses = Arrays.stream(clazz.getConstructors()[0].getParameters()).map(Parameter::getType).collect(Collectors.toSet());
        return argumentClasses.stream().map(Injectable::getInstanceOf).toArray();
    }
}
