import java.util.*;


class Vertex {
    String name;
    boolean visited;
    Vertex predecessor;
    Edge adjList[];
    int distance = Integer.MAX_VALUE;
    Vertex(String name, int size) {
        this.name = name;
        this.adjList = new Edge[size];
    }
    @Override
    public String toString(){
        return this.name;
    }
}
class Edge {
    int weight;
    Vertex startVertex,targetVertex;
    Edge(int weight,Vertex startVertex,Vertex targetVertex) {
        this.weight = weight;
        this.startVertex = startVertex;
        this.targetVertex = targetVertex;
    }
}

public class DijkstraOther {

    static void disjkstra(Vertex startVertex) {
        PriorityQueue<Vertex> pq = new PriorityQueue<>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex a,Vertex b) {
                return Integer.compare(a.distance, b.distance);
            }
        });

        startVertex.distance = 0;
        pq.add(startVertex);

        while(!pq.isEmpty()) {
            Vertex actual = pq.remove();
            for(Edge edge : actual.adjList) {
                int newDistance = actual.distance + edge.weight;
                Vertex targetVertex = edge.targetVertex;
                if(newDistance < targetVertex.distance) {
                    targetVertex.distance = newDistance;
                    targetVertex.predecessor = actual;
                    pq.remove(targetVertex);
                    pq.add(targetVertex);
                }
            }
        }
    }

    static void printPath(Vertex vertex) {
        if(vertex == null) return;
        printPath(vertex.predecessor);
        System.out.println(vertex);
    }

    public static void main(String args[]) {
        Vertex v0 = new Vertex("0", 2);
        Vertex v1 = new Vertex("1", 1);
        Vertex v2 = new Vertex("2", 0);

        v0.adjList[0] = new Edge(1,v0,v1);
        v0.adjList[1] = new Edge(4,v0,v2);
        v1.adjList[0] = new Edge(2, v1,v2);

        disjkstra(v0);

        printPath(v2);

    }

}
