package fixture;

import info.developia.gti.Gti;
import info.developia.gti.Injection;

public class Launcher {

    @Injection
    private Library library;

    public static void main(String[] args) {
        var launcher = new Launcher();

        Gti.start(Launcher.class);
        launcher.library.process();
    }
}
