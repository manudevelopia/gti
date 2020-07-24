package info.developia.gti

import spock.lang.Specification

class GtiSpec extends Specification {

    def setup() {
    }

    def "test start"() {
        given:
        Gti gti = new Gti()
        when:
        gti.start()
        then:
        !gti.getInjectables().isEmpty()
    }
}
