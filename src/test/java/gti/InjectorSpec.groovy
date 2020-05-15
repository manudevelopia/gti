package gti

import info.developia.gti.Injector
import fixture.Library
import spock.lang.Specification

class InjectorSpec extends Specification {
    def "Injector should provide a instance of expected object"() {
        given:
        Injector injector = new Injector(Library.class);

        when:
        Library result = (Library) injector.getInstanceFor(Library.class);

        then:
        result instanceof Library
    }
}
