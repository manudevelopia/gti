package fixture;

import info.developia.gti.Injector;

public class Launcher {
    private static final Injector injector = new Injector();

    public static void main(String[] args) {
        Library library = (Library) injector.getInstanceOf(Library.class);
        library.process();
    }
}
