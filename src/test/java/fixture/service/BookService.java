package fixture.service;

import fixture.model.Book;
import fixture.repository.BookRepository;
import info.developia.gti.Inject;
import info.developia.gti.Injection;

@Injection
public class BookService {
    @Inject
//    private BookRepository bookRepository;
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void add(Book book) {
        bookRepository.add(book);
    }

    public void get(Book book) {
        bookRepository.get(book);
    }

    public void del(Book book) {
        bookRepository.del(book);
    }
}
