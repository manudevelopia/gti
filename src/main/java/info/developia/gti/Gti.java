package info.developia.gti;

import java.util.HashSet;
import java.util.List;

public class Gti {
    private static Gti gti;
    private final InstanceHelper instanceHelper = new InstanceHelper();

    public static Gti inject() {
        return instance();
    }

    private static Gti instance() {
        if (gti == null) gti = new Gti();
        return gti;
    }

    public Gti packageBase(String packageName) {
        instanceHelper.setPackageBase(packageName);
        return this;
    }

    public Gti with(Object object) {
        instanceHelper.addInstance(object);
        return this;
    }

    public Gti withAs(Object object, String keyName){
        instanceHelper.addInstance(keyName, object);
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
        if (instanceHelper.getPackageBase() == null) {
            setDefaultPackageBase(clazz.getPackageName());
        }
        return instanceHelper.buildInstance(clazz, new HashSet<>());
    }

    private void setDefaultPackageBase(String packageName) {
        var packageBase = packageName.substring(0, nthIndexOf(packageName, ".", 2));
        instanceHelper.setPackageBase(packageBase);
    }

    int nthIndexOf(String input, String substring, int nth) {
        if (nth == 1) {
            return input.indexOf(substring);
        } else {
            return input.indexOf(substring, nthIndexOf(input, substring, nth - 1) + substring.length());
        }
    }

    public static <T> T get(Class<T> clazz) {
        if (!instance().instanceHelper.contains(clazz)) {
            throw new GtiException("No instance found for " + clazz.getName());
        }
        return clazz.cast(instance().instanceHelper.get(clazz));
    }

    public static void stop() {
        instance().instanceHelper.clear();
    }
}
