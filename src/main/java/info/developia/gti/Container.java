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

public class Container {
    private final Map<Class<?>, Object> instances = new HashMap<>();

    public static <T> T startOn(Class<T> clazz) {
        Container container = new Container();
        var injectables = container.getFieldsToInject(clazz.getPackageName());
        injectables.forEach(field -> container.initialize(field.getType()));
        injectables.forEach(field -> container.initialize(field.getDeclaringClass()));
        injectables.forEach(container::injectField);
        return container.getInstance(clazz);
    }

    private void initialize(Class<?> clazz) {
        if (instances.containsKey(clazz)) return;
        var constructorFiled = clazz.getConstructors()[0];
        var constructorArguments = getConstructorArgs(constructorFiled);
        try {
            instances.put(clazz, constructorFiled.newInstance(constructorArguments));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new InjectionException("Field cannot be initialized " + e.getMessage());
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
        return reflections.getFieldsAnnotatedWith(Inject.class).stream().distinct()
                .sorted(Comparator.comparing(f -> f.getType().getConstructors()[0].getParameters().length))
                .collect(Collectors.toList());
    }

    private <T> T getInstance(Class<T> clazz) {
        T instance = clazz.cast(instances.get(clazz));
        if (instance == null) {
            throw new InjectionException("No instance found for " + clazz.getName());
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

