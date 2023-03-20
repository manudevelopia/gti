package info.developia.gti.graph;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String name;
    private final List<Edge> edges = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addEdge(Node destination) {
        edges.add(new Edge(this, destination));
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
