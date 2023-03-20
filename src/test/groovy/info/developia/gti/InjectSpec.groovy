package info.developia.gti

import fixture.Launcher
import spock.lang.Specification

class InjectSpec extends Specification {

    def "test"() {
        given:
        var launcher = Container.startOn(Launcher)
        when:
        launcher.library.process()
        then:
        noExceptionThrown()
    }
}
