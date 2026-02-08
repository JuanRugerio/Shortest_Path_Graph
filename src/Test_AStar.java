package org.example;

import org.example.Grafo;
import org.example.Closest;
import org.example.Nodo;
import org.example.AStar;
import org.example.DijkstraB;
import jakarta.json.JsonObject;

import java.util.List;

public class Test_AStar {
    public static void main(String[] args) throws Exception {

        String myPath = "C:/Users/juanr/MavenJRRB/src/main/java/org/example/schleswig-holstein.json";

        Closest n1 = Grafo.readRoadNetwork(myPath, false);
        AStar a_star1 = new AStar(n1);

        Nodo node1 = n1.getNodes().get(433);
        Nodo node2 = n1.getNodes().get(11);

        System.out.println(node1);
        System.out.println(node2);

        AStar a1 = new AStar(n1);
        List<Nodo> result_astar = a1.find_shortest_Path_anyLocation(node1.getLatitude(), node1.getLongitude(), node2.getLatitude(), node2.getLongitude());
        JsonObject res1 = a1.convert_A_star_output_to_GeoJson(result_astar);
        JsonObject res2 = a1.convert_A_star_output_to_GeoJson_jakarta(result_astar);
        System.out.println(res1);
        System.out.println(res2);

        /**
Guarda el path del archivo, lee archivo y pasa a una lista con la función de Grafo y luego, ejecuta el constructor
         de AStar con ese grafo. Toma dos nodos del Grafo, los imprime, ejecuta la función AStar anylocation
         con las 4 coordenadas. Convierte el resultado del algoritmo usando ambos métodos de conversión a JSon
         e imprime
         */


    }
}
