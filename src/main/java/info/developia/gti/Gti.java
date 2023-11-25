package info.developia.gti;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class Gti {
    private final Map<Class<?>, Object> instances = new ConcurrentSkipListMap<>(Comparator.comparing(Class::getName));
    private static Gti gti;

    private static Gti instance() {
        if (gti == null) gti = new Gti();
        return gti;
    }

    public static <T> T startOn(Class<T> clazz) {
        return instance().buildInstance(clazz, new HashSet<>());
    }

    private <T> T buildInstance(Class<T> clazz, Set<Object> visitedClasses) {
        checkCircularDependency(clazz, visitedClasses);
        var constructor = getConstructor(clazz);
        var arguments = getArguments(constructor, visitedClasses);
        try {
            var instance = clazz.cast(constructor.newInstance(arguments));
            instances.put(clazz, instance);
            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new GtiException("Field cannot be initialized " + e.getMessage());
        }
    }

    private <T> Constructor<?> getConstructor(Class<T> clazz) {
        return clazz.getConstructors()[0];
    }

    private <T> void checkCircularDependency(Class<T> clazz, Set<Object> visitedClasses) {
        if (visitedClasses.contains(clazz)) {
            throw new GtiException("Circular dependency detected, " + clazz.getName() + " have been previously referenced.");
        }
        visitedClasses.add(clazz);
    }

    private Object[] getArguments(Constructor<?> constructor, Set<Object> visitedClasses) {
        return Arrays.stream(constructor.getParameterTypes())
                .map(type -> instances.computeIfAbsent(type, clazz -> buildInstance(clazz, visitedClasses)))
                .toArray();
    }

    public static <T> T get(Class<T> clazz) {
        T instance = clazz.cast(instance().instances.get(clazz));
        if (instance == null) {
            throw new GtiException("No instance found for " + clazz.getName());
        }
        return instance;
    }

    public static void stop() {
        instance().instances.clear();
    }
}

