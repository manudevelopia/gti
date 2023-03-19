package info.developia.gti.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<String, Node> nodes = new HashMap<>();

    public void addNode(Node node) {
        nodes.put(node.getName(), node);
    }

    public void addNodes(Node... node) {
        for (Node value : node) {
            addNode(value);
        }
    }

    public List<Node> traverse(Node start) {
        List<Node> visited = new ArrayList<>();
        traverseHelper(start, visited);
        return visited;
    }

    private void traverseHelper(Node vertex, List<Node> visited) {
        visited.add(vertex);
        for (Edge edge : vertex.getEdges()) {
            Node neighbor = edge.getDestination();
            if (!visited.contains(neighbor)) {
                traverseHelper(neighbor, visited);
            }
        }
    }
}
