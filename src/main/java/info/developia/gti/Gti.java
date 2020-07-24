package info.developia.gti;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Gti {
    private final List<String> packages;
    private Set<Class<?>> injectables;

    public Gti(String... packages) {
        this.packages = packages.length == 0 ? List.of("") : Arrays.asList(packages);
    }

    public void start() {
        injectables = packages.stream()
                .flatMap(packageName -> Injectable.getInjectables(packageName).stream())
                .collect(Collectors.toSet());
        Set<Class<?>> noParamConstructor = Injectable.getNoParamsConstructor(injectables);
    }

    public Set<Class<?>> getInjectables() {
        return injectables;
    }
}
