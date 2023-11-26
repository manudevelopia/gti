package info.developia.gti


import fixture.Library
import fixture.repository.BookRepository
import fixture.service.AnotherService
import fixture.service.AnyService
import fixture.service.BookService
import fixture.service.OtherService
import spock.lang.Specification

class GtiSpec extends Specification {

    def setup() {
        Gti.startOn(Library.class)
    }

    def cleanup() {
        Gti.stop()
    }

    def "Should return a instance of expected object on startOn"() {
        when:
        var result = Gti.startOn(Library.class)
        then:
        result instanceof Library
    }

    def "Should return a instance of expected object on get"() {
        expect:
        def result = Gti.get(clazz)
        clazz.isCase(result)
        where:
        clazz << [AnyService, AnotherService, BookService, BookRepository, OtherService]
    }

    def "Should not have any instances after stop"() {
        given:
        Gti.stop()
        when:
        Gti.get(Library)
        then:
        var exception = thrown(GtiException.class)
        exception.message == "No instance found for fixture.Library"
    }
}
