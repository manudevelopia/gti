package test.developia.fixture;

import test.developia.fixture.service.AnotherNoDependencyService;
import test.outside.fixture.service.util.Mapper;
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
