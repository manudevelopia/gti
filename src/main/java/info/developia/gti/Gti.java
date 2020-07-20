package info.developia.gti;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Gti {
    private List<String> packages;

    public Gti(String... packages) {
        this.packages = Arrays.asList(packages);
    }

    public Gti(Class<?> clazz){
        packages.stream().map(Injectable::getInjectables).collect(Collectors.toList());
    }

    public void start(){
        packages.stream().map(Injectable::getInjectables).collect(Collectors.toList());
    }
}
