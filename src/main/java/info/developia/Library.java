package info.developia;

import info.developia.gti.Injection;
import info.developia.model.Book;
import info.developia.service.BookService;

@Injection
public class Library {

    private final BookService bookService;

    public Library() {
        this.bookService = new BookService();
    }

    public void process() {
        Book book = new Book();
        System.out.println(book);
        bookService.add(book);
        bookService.get(book);
        bookService.del(book);
    }
}
