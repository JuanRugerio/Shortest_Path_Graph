package org.example;

import java.util.ArrayList;
import java.util.List;

public class Nodo {
    double longitude;
    double latitude;

    List<Nodo>  adjacentNodes; //store nodes which are neighbors to this node

    public Nodo(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.adjacentNodes = new ArrayList<>();
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void addAdjacentNode(Nodo n){
        this.adjacentNodes.add(n);
    }
    public List<Nodo> getAdjacentNodes(){
        return this.adjacentNodes;
    }

    private static double convertsdtor(double degree) {
        return degree * (Math.PI/180);
    }
    private static double distancehaversine(double lon1, double lat1, double lon2, double lat2){

        double Radio = 6371;
        double rLat = convertsdtor(lat2-lat1);
        double rLon = convertsdtor(lon2-lon1);
        double a =
                Math.sin(rLat/2) * Math.sin(rLat/2) + Math.cos(convertsdtor(lat1)) * Math.cos(convertsdtor(lat2)) *
                        Math.sin(rLon/2) * Math.sin(rLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = Radio * c;
        return d;
    }

    public static double get_Haversine_distance(Nodo n1, Nodo n2){
        //compute the Haversine distance between two nodes
        double lon1 = n1.getLongitude();
        double lat1 = n1.getLatitude();

        double lon2 = n2.getLongitude();
        double lat2 = n2.getLatitude();

        return distancehaversine(lon1, lat1, lon2, lat2);
    }

    @Override
    public String toString() {
        return "Nodo{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}

/**
 * Clase Nodo: Tiene dos variables double para las coordenadas, la lista de adyacencia a otras clases de su
 * mismo tipo. Tiene su contructor. Un método para definir la longitud y otro para obtenerla, lo mismo para
 * longitud y luego un método para adherir un elemento a la lista de adyacencia y otro para obtenerla.
 *
 * Un método que recibe un double y te regresa por pi entre 180, para convertir de grados a radianes.
 * Otro metodo que recibe las 4 coordenadas, y te calcula la distancia en kilómetros entre ellas con la fórmula
 * de Haversine.
 *
 * Una función que recibe dos nodos, extrae su latitud y su longitud y calcula la distancia entre ellos
 *
 * Impresion
 * */
