package info.developia.gti;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Gti {
    private final Set<Class<?>> injectables;
    private final Map<String, Object> instances;

    public Gti(String... packages) {
        injectables = getPackages(packages).stream()
                .flatMap(packageName -> Injectable.getInjectables(packageName).stream())
                .collect(Collectors.toSet());
        instances = Injectable.initialize(injectables);
    }

    private List<String> getPackages(String[] packages) {
        return packages.length == 0 ? List.of("") : Arrays.asList(packages);
    }

    public Object getInstanceOf(Class<?> clazz) {
        return instances.get(clazz.getCanonicalName());
    }

    public Set<Class<?>> getInjectables() {
        return injectables;
    }
}
