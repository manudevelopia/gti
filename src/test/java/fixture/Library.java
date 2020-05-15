package fixture;

import fixture.model.Book;
import fixture.service.BookService;
import info.developia.gti.Injection;

@Injection
public class Library {

    private final BookService bookService;

    public Library(BookService bookService) {
        this.bookService = bookService;
    }

    public void process() {
        Book book = new Book();
        System.out.println(book);
        bookService.add(book);
        bookService.get(book);
        bookService.del(book);
    }
}
