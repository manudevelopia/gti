package info.developia.gti


import fixture.Library
import fixture.repository.BookRepository
import fixture.service.AnotherNoDependencyService
import fixture.service.AnyService
import fixture.service.BookService
import fixture.service.NoDependencyService
import fixture.service.OutsideDependencyService
import lib.util.Mapper
import spock.lang.Specification

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
        exception.message == "No instance found for fixture.Library"
    }

    def "Should return an error complaining about external"(){
        when:
        Gti.inject().startOn(OutsideDependencyService.class)
        then:
        var exception = thrown(GtiException.class)
        exception.message == "Not all the arguments could be fulfilled for fixture.service.OutsideDependencyService constructor"
    }
}
