package fixture;

import fixture.model.Book;
import fixture.service.AnyService;
import fixture.service.BookService;

public class Library {
    private final BookService bookService;

    private final AnyService anyService;

    public Library(BookService bookService, AnyService anyService) {
        this.bookService = bookService;
        this.anyService = anyService;
    }

    public void process() {
        Book book = new Book();
        System.out.println(book);
        bookService.add(book);
        bookService.get(book);
        bookService.del(book);

        anyService.start();
        anyService.run();
    }
}
