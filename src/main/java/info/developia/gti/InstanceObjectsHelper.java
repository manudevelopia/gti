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
    private String packageBase;

    public <T> T buildInstance(Class<T> clazz, Set<Object> visitedClasses) {
        checkCircularDependency(clazz, visitedClasses);
        var constructor = getFirstConstructor(clazz);
        var parametersInstances = getConstructorParametersInstances(constructor, visitedClasses);
        try {
            var instance = constructor.newInstance(parametersInstances);
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

    private Object[] getConstructorParametersInstances(Constructor<?> constructor, Set<Object> visitedClasses) {
        var parametersInstances = Arrays.stream(constructor.getParameterTypes())
                .filter(this::checkPackageBase)
                .map(type ->instanceObjects.computeIfAbsent(type, clazz -> buildInstance(clazz, visitedClasses)))
                .toArray();
        if (constructor.getParameterTypes().length != parametersInstances.length)
            throw new GtiException("Not all the arguments could be fulfilled for " + constructor.getName() + " constructor");
        return parametersInstances;
    }

    private boolean checkPackageBase(Class<?> clazz) {
        return clazz.getPackageName().startsWith(packageBase);
    }

    public void setPackageBase(String packageBase) {
        if (this.packageBase == null)
            this.packageBase = packageBase;
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
