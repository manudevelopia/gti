package info.developia.gti;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class Gti {
    private final Map<Class<?>, Object> instances = new HashMap<>();

    public static <T> T startOn(Class<T> clazz) {
        Gti gti = new Gti();
        var injectables = gti.getFieldsToInject(clazz.getPackageName());
        injectables.forEach(field -> gti.initialize(field.getType()));
        injectables.forEach(field -> gti.initialize(field.getDeclaringClass()));
        injectables.forEach(gti::injectField);
        return gti.getInstance(clazz);
    }

    private void initialize(Class<?> clazz) {
        if (instances.containsKey(clazz)) return;
        var constructor = clazz.getConstructors()[0];
        var arguments = getConstructorArgs(constructor);
        try {
            instances.put(clazz, constructor.newInstance(arguments));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new GtiException("Field cannot be initialized " + e.getMessage());
        }
    }

    private Object[] getConstructorArgs(Constructor<?> constructor) {
        Set<Class<?>> argumentClasses = Arrays.stream(constructor.getParameters()).map(Parameter::getType).collect(Collectors.toSet());
        return argumentClasses.stream().map(instances::get).toArray();
    }

    private List<Field> getFieldsToInject(String packageName) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageName))
                .setScanners(Scanners.FieldsAnnotated));
        return reflections.getFieldsAnnotatedWith(Injector.class).stream().distinct()
                .sorted(Comparator.comparing(f -> f.getType().getConstructors()[0].getParameters().length))
                .collect(Collectors.toList());
    }

    private <T> T getInstance(Class<T> clazz) {
        T instance = clazz.cast(instances.get(clazz));
        if (instance == null) {
            throw new GtiException("No instance found for " + clazz.getName());
        }
        return instance;
    }

    private void injectField(Field field) {
        field.setAccessible(true);
        try {
            var object = getInstance(field.getDeclaringClass());
            var dependency = getInstance(field.getType());
            field.set(object, dependency);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

