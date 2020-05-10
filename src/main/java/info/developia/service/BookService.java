package info.developia.service;

import info.developia.gti.Injection;
import info.developia.model.Book;
import info.developia.repository.BookRepository;

@Injection
public class BookService {

    private final BookRepository bookRepository;

    public BookService() {
        this.bookRepository = new BookRepository();
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
