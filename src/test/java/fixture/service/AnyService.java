package fixture.service;

public class AnyService {
    private final NoDependencyService noDependencyService;
    private final AnotherNoDependencyService anotherNoDependencyService;

    public AnyService(AnotherNoDependencyService anotherNoDependencyService, NoDependencyService noDependencyService) {
        this.noDependencyService = noDependencyService;
        this.anotherNoDependencyService = anotherNoDependencyService;
    }

    public void start() {
        anotherNoDependencyService.run();
        noDependencyService.run();
    }

    public void run() {
        System.out.println("Hello to from AnyService run()");
    }
}
