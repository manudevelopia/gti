package fixture.service;

import info.developia.gti.Injector;

public class AnyService {
    @Injector
    private final OtherService otherService;
    @Injector
    private final AnotherService anotherService;

    public AnyService(AnotherService anotherService, OtherService otherService) {
        this.otherService = otherService;
        this.anotherService = anotherService;
    }

    public void start() {
        anotherService.run();
        otherService.run();
    }

    public void run() {
        System.out.println("Hello to from AnyService run()");
    }
}
