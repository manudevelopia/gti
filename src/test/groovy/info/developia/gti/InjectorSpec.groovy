package info.developia.gti

import fixture.Launcher
import spock.lang.Specification

class InjectorSpec extends Specification {

    def "test"() {
        given:
        var launcher = Gti.startOn(Launcher)
        when:
        launcher.library.process()
        then:
        noExceptionThrown()
    }
}
