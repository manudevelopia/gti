package info.developia.gti;

public class Key {
    private final String name;
    private final Class<?> clazz;

    public Key(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
