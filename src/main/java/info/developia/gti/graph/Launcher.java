package info.developia.gti.graph;

public class Launcher {

    public static void main(String[] args) {
        Graph graph = new Graph();

        Node library = new Node("Library");
        Node bookService = new Node("Book Service");
        Node userService = new Node("User Service");
        Node bookRepository = new Node("Book Repository");

        graph.addNodes(library, bookService, userService, bookRepository);

        library.addEdge(bookService);
        library.addEdge(userService);
        bookService.addEdge(bookRepository);

        var items = graph.traverse(library);
    }
}
