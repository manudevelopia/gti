package info.developia.gti;

import java.util.HashSet;
import java.util.List;

public class Gti {
    private static Gti gti;
    private final InstanceObjectsHelper instanceObjectHelper = new InstanceObjectsHelper();

    public static Gti inject() {
        return instance();
    }

    private static Gti instance() {
        if (gti == null) gti = new Gti();
        return gti;
    }

    public Gti packageBase(String packageName){
        instanceObjectHelper.setPackageBase(packageName);
        return this;
    }

    public Gti with(Object object) {
        instanceObjectHelper.add(object);
        return this;
    }

    public Gti with(List<Object> objects) {
        objects.forEach(this::with);
        return this;
    }

    public Gti with(Object... objects) {
        for (Object object : objects) with(object);
        return this;
    }

    public <T> T startOn(Class<T> clazz) {
        instanceObjectHelper.setPackageBase(clazz.getPackageName());
        return instanceObjectHelper.buildInstance(clazz, new HashSet<>());
    }

    public static <T> T get(Class<T> clazz) {
        if (!instance().instanceObjectHelper.contains(clazz)) {
            throw new GtiException("No instance found for " + clazz.getName());
        }
        return clazz.cast(instance().instanceObjectHelper.get(clazz));
    }

    public static void stop() {
        instance().instanceObjectHelper.clear();
    }
}
