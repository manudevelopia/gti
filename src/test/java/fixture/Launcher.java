package fixture;

import info.developia.gti.Container;
import info.developia.gti.Inject;

public class Launcher {

    @Inject
    private Library library;

    public static void main(String[] args) {
        var launcher = Container.startOn(Launcher.class);
        launcher.library.process();
    }
}
