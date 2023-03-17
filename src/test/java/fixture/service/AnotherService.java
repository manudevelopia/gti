package fixture.service;

import info.developia.gti.Injection;

@Injection
public class AnotherService {
    private final AnyService anyService;

    public AnotherService(AnyService anyService) {
        this.anyService = anyService;
    }

    public void run() {
        System.out.println("Hello from AnotherService run()");
    }
}
