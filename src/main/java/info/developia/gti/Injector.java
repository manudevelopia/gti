package info.developia.gti;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Injector {

    public Map<String, Object> instances = new HashMap<>();

    public void add(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Injection.class)) {
            Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
            try {
                instances.put(clazz.getCanonicalName(), constructor.newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Injection.class)) {
                Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
                try {
                    instances.put(clazz.getCanonicalName(), constructor.newInstance());
                    field.setAccessible(true);
                    field.set(null, constructor.newInstance());
                    field.setAccessible(false);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
