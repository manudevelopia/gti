package info.developia.gti;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

public class InstanceObjectsHelper {
    private final Map<Class<?>, Object> instanceObjects = new ConcurrentSkipListMap<>(Comparator.comparing(Class::getName));

    public <T> T buildInstance(Class<T> clazz, Set<Object> visitedClasses) {
        checkCircularDependency(clazz, visitedClasses);
        var constructor = getFirstConstructor(clazz);
        var arguments = getArguments(constructor, visitedClasses);
        try {
            var instance = constructor.newInstance(arguments);
            instanceObjects.put(clazz, instance);
            return clazz.cast(instance);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new GtiException("Field cannot be initialized " + e.getMessage());
        }
    }

    private <T> void checkCircularDependency(Class<T> clazz, Set<Object> visitedClasses) {
        if (visitedClasses.contains(clazz)) {
            throw new GtiException("Circular dependency detected, " + clazz.getName() + " have been previously referenced.");
        }
        visitedClasses.add(clazz);
    }

    private <T> Constructor<?> getFirstConstructor(Class<T> clazz) {
        return clazz.getConstructors()[0];
    }

    private Object[] getArguments(Constructor<?> constructor, Set<Object> visitedClasses) {
        return Arrays.stream(constructor.getParameterTypes())
                .map(type -> instanceObjects.computeIfAbsent(type, clazz -> buildInstance(clazz, visitedClasses)))
                .toArray();
    }

    public void add(Object object) {
        instanceObjects.put(object.getClass(), object);
    }

    public void clear() {
        instanceObjects.clear();
    }

    public <T> boolean contains(Class<T> clazz) {
        return instanceObjects.containsKey(clazz);
    }

    public <T> Object get(Class<T> clazz) {
        return instanceObjects.get(clazz);
    }
}
