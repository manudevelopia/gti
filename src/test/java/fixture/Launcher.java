package fixture;

import info.developia.gti.Injector;

public class Launcher {

    public static void main(String[] args) {
        Injector injector = new Injector(Library.class);
        Library library = (Library) injector.getInstanceFor(Library.class);
        library.process();
    }
}
