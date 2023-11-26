package fixture.service;

public class AnyService {
    private final OtherService otherService;
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
