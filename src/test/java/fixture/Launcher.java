package fixture;

import fixture.service.AnotherNoDependencyService;
import lib.util.Mapper;
import info.developia.gti.Gti;

public class Launcher {

    public static void main(String[] args) {
        Mapper mapper = new Mapper();
        Library library = Gti.inject().with(mapper).startOn(Library.class);
        library.process();

        var anotherService = Gti.get(AnotherNoDependencyService.class);
        anotherService.run();

        Gti.stop();
    }
}
