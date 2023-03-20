package fixture.service;

import info.developia.gti.Injector;

public class AnotherService {
    @Injector
    private final AnyService anyService;

    public AnotherService(AnyService anyService) {
        this.anyService = anyService;
    }

    public void run() {
        System.out.println("Hello from AnotherService run()");
    }
}
