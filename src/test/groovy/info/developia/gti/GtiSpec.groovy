package info.developia.gti

import fixture.Launcher
import fixture.Library
import spock.lang.Specification

class GtiSpec extends Specification {
    def "Injector should provide a instance of expected object"() {
        when:
        var result = Gti.startOn(Library)
        then:
        result instanceof Library
    }

    def "test"() {
        given:
        var launcher = Gti.startOn(Launcher)
        when:
        launcher.library.process()
        then:
        noExceptionThrown()
    }
}
