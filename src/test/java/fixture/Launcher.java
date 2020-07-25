package fixture;

import info.developia.gti.Gti;

public class Launcher {

    public static void main(String[] args) {
        Gti gti = new Gti();
        Library library = (Library) gti.getInstanceOf(Library.class);
        library.process();
    }
}
