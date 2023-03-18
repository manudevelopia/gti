package fixture.repository;

import fixture.model.Book;
import info.developia.gti.Injection;

@Injection
public class BookRepository {
    public void add(Book book) {
        System.out.println(book + " book has been added");
    }

    public void get(Book book) {
        System.out.println("Here you are book: " + book);
    }

    public void del(Book book) {
        System.out.println(book + " book has been removed");
    }
}
