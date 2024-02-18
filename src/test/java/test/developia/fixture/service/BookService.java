package test.developia.fixture.service;

import test.developia.fixture.model.Book;
import test.developia.fixture.repository.BookRepository;

public class BookService {
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
