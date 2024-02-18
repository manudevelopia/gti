package info.developia.gti

import spock.lang.Specification
import test.developia.fixture.Library
import test.developia.fixture.repository.BookRepository
import test.developia.fixture.service.OutsideDependencyService
import test.outside.fixture.service.util.Mapper
import test.developia.fixture.service.AnyService
import test.developia.fixture.service.AnotherNoDependencyService
import test.developia.fixture.service.BookService
import test.developia.fixture.service.NoDependencyService

class GtiSpec extends Specification {

    def cleanup() {
        Gti.stop()
    }

    def "Should return a instance of expected object on startOn"() {
        when:
        var result = Gti.inject().startOn(Library.class)
        then:
        result instanceof Library
    }

    def "Should return a instance of expected object and also the added one"() {
        given:
        var mapper = new Mapper()
        when:
        var result = Gti.inject().with(mapper).startOn(Library.class)
        then:
        Gti.get(Mapper.class) instanceof Mapper
        result instanceof Library
    }

    def "Should return a instance of expected object on get"() {
        given:
        Gti.inject().startOn(Library.class)
        expect:
        def result = Gti.get(dependencies)
        result in dependencies
        where:
        dependencies << [AnyService, AnotherNoDependencyService, BookService, BookRepository, NoDependencyService]
    }

    def "Should not have any instances after stop"() {
        given:
        Gti.inject().startOn(Library.class)
        Gti.stop()
        when:
        Gti.get(Library)
        then:
        var exception = thrown(GtiException.class)
        exception.message.startsWith "No instance found for"
    }

    def "Should return an error complaining about external"() {
        when:
        Gti.inject().startOn(OutsideDependencyService.class)
        then:
        var exception = thrown(GtiException.class)
        exception.message.startsWith "Not all the arguments could be fulfilled for"
    }
}
