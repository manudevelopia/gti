package fixture;

import info.developia.gti.Gti;
import info.developia.gti.Injector;

public class Launcher {

    @Injector
    private final Library library;

    public Launcher(Library library) {
        this.library = library;
    }

    public static void main(String[] args) {
        var launcher = Gti.startOn(Launcher.class);
        launcher.library.process();
    }
}
