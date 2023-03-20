package fixture.service;

import fixture.model.Book;
import fixture.repository.BookRepository;
import info.developia.gti.Injector;

public class BookService {
    @Injector
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
