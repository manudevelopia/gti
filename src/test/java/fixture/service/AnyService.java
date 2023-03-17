package fixture.service;

import info.developia.gti.Injection;

@Injection
public class AnyService {
    private final AnyService anyService;
    private final AnotherService anotherService;

    public AnyService(AnyService anyService, AnotherService anotherService) {
        this.anyService = anyService;
        this.anotherService = anotherService;
    }

    public void start() {
        anotherService.run();
        anyService.run();
    }

    private void run() {
        System.out.println("Hello to from AnyService run()");
    }
}
