package org.example;


import java.util.List;

public class Closest {
    List<Nodo> nodes;  //list of nodes of network
    public Closest(List<Nodo> nodes) {
        this.nodes = nodes;
    }

    //getters and setters
    public List<Nodo> getNodes() {
        return nodes;
    }

    public void setNodes(List<Nodo> nodes) {
        this.nodes = nodes;
    }

    //find closest node in the set of nodes of the routenetwork
    public Nodo closestNode(double latitude, double longitude){
        double min_dist = Double.POSITIVE_INFINITY;
        Nodo closest_node = null;

        Nodo nodoaleatorio = new Nodo(longitude, latitude);   //create dummy node to use the Haversine_distance function

        //use simple linear scan to find closest node
        for (Nodo n : this.nodes){
            double dton = Nodo.get_Haversine_distance(nodoaleatorio, n);

            if (dton < min_dist){
                min_dist = dton;
                closest_node = n;
            }
        }
        return closest_node;
    }




}

/**
 * Tiene una lista de nodos. Un método que recibe una lista de nodos e instancia. Métodos para asentar y tomar
 * la lista de nodos. Función que recibe latitud y longitud, inicialmente distancia minima a infinito,
 * y variable tipo nodo para el mas cercano vacía, Instancia el nodo con las coordenadas recibidas
 * , recorre todos los nodos en la lista y
 * va guardando el que menor distancia tiene, junto con esta distancia, regresa el nodo mas cercano
 */