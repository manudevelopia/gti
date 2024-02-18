package test.developia.fixture.service;

import test.outside.fixture.service.util.Mapper;

public class OutsideDependencyService {
    private final Mapper mapper;

    public OutsideDependencyService(Mapper mapper) {
        this.mapper = mapper;
    }

    public void run() {
        System.out.println(mapper.capitol("Hello from AnotherNoDependencyService run()"));
    }
}
