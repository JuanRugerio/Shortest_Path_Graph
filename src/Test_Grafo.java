package org.example;

import org.example.Grafo;
import org.example.Closest;
import org.example.Nodo;

public class Test_Grafo {
    public static void main(String[] args) throws Exception {

        String myPath = "C:/Users/juanr/MavenJRRB/src/main/java/org/example/schleswig-holstein.json";

        Closest n1 = Grafo.readRoadNetwork(myPath, false);
        System.out.println(n1.getNodes().size());

    }
}
/**
 * Clase Test_Grafo: Declara una clase tipo closest (lista de nodos) y la iguala a la salida del método
 * readRoadNetwork de la clase Grafo. Imprime número de nodos.
 */
