package info.developia.gti;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

class InstanceHelper {
    private final Map<Key, Object> instances = new ConcurrentSkipListMap<>(Comparator.comparing(Key::getName));
    private String packageBase;

    <T> T buildInstance(Class<T> clazz, Set<Object> visitedClasses) {
        checkCircularDependency(clazz, visitedClasses);
        var constructor = getFirstConstructor(clazz);
        var parametersInstances = getConstructorParametersInstances(constructor, visitedClasses);
        try {
            var instance = constructor.newInstance(parametersInstances);
            addInstance(clazz, instance);
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

    private Object[] getConstructorParametersInstances(Constructor<?> constructor, Set<Object> visitedClasses) {
        var parametersInstances = Arrays.stream(constructor.getParameterTypes())
                .filter(this::isObjectReachable)
                .map(type -> getInstance(type, visitedClasses))
                .toArray();
        if (constructor.getParameterTypes().length != parametersInstances.length)
            throw new GtiException("Not all the arguments could be fulfilled for " + constructor.getName() + " constructor");
        return parametersInstances;
    }

    private Object getInstance(Class<?> type, Set<Object> visitedClasses) {
        Key key = new Key(type.getName(), type);
        return instances.computeIfAbsent(key, clazz -> buildInstance(clazz.getClazz(), visitedClasses));
    }

    private boolean isObjectReachable(Class<?> clazz) {
        return contains(clazz) || clazz.getPackageName().startsWith(packageBase);
    }

    String getPackageBase() {
        return packageBase;
    }

    void setPackageBase(String packageBase) {
        this.packageBase = packageBase;
    }

    void addInstance(Object object) {
        Key key = new Key(object.getClass().getName(), object.getClass());
        instances.put(key, object);
    }

    <T> void addInstance(Class<T> clazz, Object instance) {
        Key key = new Key(clazz.getName(), clazz);
        instances.put(key, instance);
    }

    void addInstance(String keyName, Object object) {
        Key key = new Key(keyName, object.getClass());
        instances.put(key, object);
    }

    public void clear() {
        instances.clear();
    }

    public <T> boolean contains(Class<T> clazz) {
        Key key = new Key(clazz.getName(), clazz);
        return instances.containsKey(key);
    }

    public <T> Object get(Class<T> clazz) {
        return instances.get(new Key(clazz.getName(), clazz));
    }
}
