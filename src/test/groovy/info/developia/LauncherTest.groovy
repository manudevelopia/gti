package info.developia

import spock.lang.Specification

class LauncherTest extends Specification {
    def "application has a greeting"() {
        setup:
        def app = new Launcher()

        when:
        def result = app.greeting

        then:
        result != null
    }
}
