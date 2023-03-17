package fixture.service;

import info.developia.gti.Injection;

@Injection
public class OtherService {
    private final AnyService anyService;

    public OtherService(AnyService anyService) {
        this.anyService = anyService;
    }
}
