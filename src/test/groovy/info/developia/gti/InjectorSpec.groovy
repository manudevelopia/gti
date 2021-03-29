package info.developia.gti

import fixture.Launcher
import fixture.Library
import spock.lang.Specification

class InjectorSpec extends Specification {
    def "Injector should provide a instance of expected object"() {
        given:
        Injector injector = Injector.start(Launcher)
        when:
        Library result = (Library) injector.getInstanceOf(Library.class)
        then:
        result instanceof Library
    }
}
