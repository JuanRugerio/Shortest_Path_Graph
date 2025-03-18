package org.example;
import java.util.List;

public class EdgeWeightedDirectedGraph {
    private Vertex[] vertices;

    public EdgeWeightedDirectedGraph(int v) {
        vertices = new Vertex[v];

        for (int i = 0; i < v; i++) {
            vertices[i] = new Vertex(i);
        }
    }

    public void addEdge(int v, int w, double v1, double v2, double w1, double w2) {
        Edge edge = new Edge(v, w, v1, v2, w1, w2);
        vertices[v].addEdge(edge);
    }

    public List<Edge> getAdj(int v) {
        return vertices[v].getAdj();
    }

    public static void main (String[] args) {
        EdgeWeightedDirectedGraph graph = new EdgeWeightedDirectedGraph(3);

        graph.addEdge(0,1,9.8446954, 54.4565293, 9.8450164, 54.4566301);
        graph.addEdge(1,2, 9.8450164, 54.4566301, 9.8657649,54.4461501);
        graph.addEdge(0,2, 9.8446954, 54.4565293, 9.8657649,54.4461501);

        List<Edge> adj = graph.getAdj(0);
        for (Edge edge : adj) {
            System.out.println(edge);
        }
    }
}

/**
 * First specifies packages and imports. Defines a class. Defines an array of vertexes called vertices.
 * Constructor: Receives the number of vertexes the Graph has, creates an array with that number of vertexes
 * for each space it actually creates the vertex. Method addEdge, receives from to and weight, creates the edge,
 * and adds it to the corresponding vertex. Method that is provided with a vertex and returns its adjacency list
 * At the main it instantiates a graph with 3 vertexes. Calls method that adds Edges to adjacency lists.
 * Prints out all Edges
 *
 */