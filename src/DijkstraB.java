package org.example;

import org.example.Closest;
import org.example.AStar;

public class DijkstraB extends AStar {
    //just use A-star without any heuristic (heuristic_fun is always zero 0)
    public DijkstraB(Closest network) {
        super(network, "none");
    }
}
