package org.example;
import java.lang.Math;

public class Edge {

    private int v, w;
    private double v1, v2, w1, w2, weight;

    public Edge(int v, int w, double v1, double v2, double w1, double w2) {
        this.v =v;
        this.w = w;
        this.v1 = v1;
        this.v2 = v2;
        this.w1 = w1;
        this.w2 = w2;
        this.weight = Math.sqrt((v1 - w1) * (v1 - w1) + (v2 - w2) * (v2 - w2));
    }

    @Override
    public String toString() {
        return "Edge{" +
                "v=" + v +
                ", w=" + w +
                ", weight=" + weight +
                "}";
    }
}

/**
 Commentary: Declares package.
 Declares public class called Edge
 Declares that this class uses two integer variables called v and w that are private to the class and another double
 called weight that is private too.
 Then declares a public constructor where the local names are the same as what the class counts with. Then, it
 instantiates the variables with the values it receives.
 A "something that I don´t understand pretty well" that prints the legend and the values
 */
