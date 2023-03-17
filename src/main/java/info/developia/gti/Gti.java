package info.developia.gti;

import java.util.*;
import java.util.stream.Collectors;

public class Gti {
    private final Map<String, Object> instances;
    private static Gti instance;

    public static Gti start(Class<?> clazz) {
        return new Gti(clazz.getPackageName());
    }

    private Gti(String... packageNames) {
        Set<String> packages = getPackages(packageNames);
        Set<Class<?>> injectables = getInjectables(packages);
        instances = Injectable.initialize(injectables);
    }

    private Set<String> getPackages(String[] packages) {
        return packages.length == 0 ? Collections.emptySet() : new HashSet<>(Arrays.asList(packages));
    }

    private Set<Class<?>> getInjectables(Set<String> packages) {
        return packages.stream()
                .flatMap(packageName -> Injectable.getInjectables(packageName).stream())
                .collect(Collectors.toSet());
    }

    public <T> T getInstanceOf(Class<T> clazz) {
        return clazz.cast(instances.get(clazz.getCanonicalName()));
    }
}
