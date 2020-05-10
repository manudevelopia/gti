package info.developia;

import info.developia.gti.Injector;

public class Launcher {

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        Injector injector = new Injector(Library.class);
        Library library = (Library) injector.getInstanceFor("info.developia.Library");
        library.process();
    }
}
