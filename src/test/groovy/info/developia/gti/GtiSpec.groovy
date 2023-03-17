package info.developia.gti

import fixture.Launcher
import fixture.Library
import spock.lang.Specification

class GtiSpec extends Specification {
    def "Injector should provide a instance of expected object"() {
        given:
        Gti injector = Gti.start(Launcher)
        when:
        def result = injector.getInstanceOf(Library.class)
        then:
        result instanceof Library
    }
}
