package info.developia.gti;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Injector {

    private final Map<String, Object> instances = new HashMap<>();

    public Injector(Class<?>... libraryClass) {
        for (Class<?> clazz : libraryClass) {
            add(clazz);
        }
    }

    public void add(Class<?> clazz) {
        processClass(clazz, Injection.class);
        processFields(clazz, Injection.class);
    }

    private void processFields(Class<?> clazz, Class<? extends Annotation> annotation) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotation)) {
                Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
                try {
                    instances.put(clazz.getCanonicalName(), constructor.newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new InjectionException();
                }
            }
        }
    }

    private void processClass(Class<?> clazz, Class<? extends Annotation> annotation) {
        if (clazz.isAnnotationPresent(annotation)) {
            Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
            try {
                instances.put(clazz.getCanonicalName(), constructor.newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new InjectionException();
            }
        }
    }

    public Object getInstanceFor(Class<?> clazz) {
        return Optional.ofNullable(instances.get(clazz.getName()))
                .orElseThrow(InjectionException::new);
    }
}
