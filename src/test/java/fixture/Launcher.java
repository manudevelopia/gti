package fixture;

import info.developia.gti.Injector;

public class Launcher {

    public static void main(String[] args) {
        Injector injector = Injector.start(Launcher.class);
        Library library = (Library) injector.getInstanceOf(Library.class);
        library.process();
    }
}
