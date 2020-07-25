package info.developia.gti;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Injector {
    private final Map<String, Object> instances;

    public Injector(String... packageNames) {
        Set<String> packages = getPackages(packageNames);
        Set<Class<?>> injectables = getInjectables(packages);
        instances = Injectable.initialize(injectables);
    }

    private Set<Class<?>> getInjectables(Set<String> packages) {
        return packages.stream()
                .flatMap(packageName -> Injectable.getInjectables(packageName).stream())
                .collect(Collectors.toSet());
    }

    private Set<String> getPackages(String[] packages) {
        return packages.length == 0 ? Set.of("") : new HashSet<>(Arrays.asList(packages));
    }

    public Object getInstanceOf(Class<?> clazz) {
        return instances.get(clazz.getCanonicalName());
    }
}
