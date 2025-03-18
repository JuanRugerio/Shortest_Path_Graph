package org.example;
import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private int id;
    private List<Edge> adjEdgeList = new ArrayList<>();

    public int getId() {return id;}

    public Vertex(int id) {this.id = id;}

    public void addEdge(Edge edge) {adjEdgeList.add(edge);}

    public List<Edge> getAdj(){
        return adjEdgeList;
    }
}

/**Commentary:
 * Packages and tools it uses.
 * One public Class: Declares it has an integer value called id, private to the class. A private List of variables
 * of type Edge, called adjEdgeList that is initilized
 * A public method that returns the id
 * A constructor that receives and instantiates the id. A constructor that receives a datatype Edge, and adds it
 * to the adjEdgeList. And a method that returns adjEdgeList
 * In general, it defines it can stock an integer and a list of Edges, ways to instantiate the id and to add Edges
 * to this id, and ways to return the id and its list of Edges
 *
 * */