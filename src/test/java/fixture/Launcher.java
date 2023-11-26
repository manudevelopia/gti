package fixture;

import fixture.service.AnotherService;
import info.developia.gti.Gti;

public class Launcher {

    public static void main(String[] args) {
        var library = Gti.startOn(Library.class);
        library.process();

        var anotherService = Gti.get(AnotherService.class);
        anotherService.run();

        Gti.stop();
    }
}
