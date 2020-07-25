package info.developia.gti;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Gti {
    private final List<String> packages;
    private Set<Class<?>> injectables;
    private final Injectable injectable;
    private Map<String, Object> instances;

    public Gti(String... packages) {
        this.packages = packages.length == 0 ? List.of("") : Arrays.asList(packages);
        injectable = new Injectable();
    }

    public void start() {
        injectables = packages.stream()
                .flatMap(packageName -> injectable.getInjectables(packageName).stream())
                .collect(Collectors.toSet());
        instances = injectable.initialize(injectables);
    }

    public Object getInstanceOf(Class<?> clazz) {
        return instances.get(clazz.getCanonicalName());
    }

    public Set<Class<?>> getInjectables() {
        return injectables;
    }
}
