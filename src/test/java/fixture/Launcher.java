package fixture;

import info.developia.gti.Gti;

public class Launcher {

    public static void main(String[] args) {
        var library = Gti.startOn(Library.class);
        library.process();
    }
}
