package org.example;

import java.util.Arrays;

public class Arista{
    Nodo start;  //also use this convention for non-directed edges
    Nodo end;
    double[][] Arraycoordinates; //Array to store all the coordinates of the edge
    int maxspeed;
    double weight;  //compute later

    public Arista(Nodo start, Nodo end) {
        this.start = start;
        this.end = end;
    }
    public Arista(Nodo start, Nodo end, double[][] arraycoordinates, int maxspeed) {
        this.start = start;
        this.end = end;
        this.Arraycoordinates = arraycoordinates;
        this.maxspeed = maxspeed;
    }


    public Nodo getStart() {
        return start;
    }

    public void setStart(Nodo start) {
        this.start = start;
    }

    public Nodo getEnd() {
        return end;
    }

    public void setEnd(Nodo end) {
        this.end = end;
    }

    public double[][] getArraycoordinates() {
        return Arraycoordinates;
    }

    public void setArraycoordinates(double[][] Arraycoordinates) {
        this.Arraycoordinates = Arraycoordinates;
    }

    public int getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(int maxspeed) {
        this.maxspeed = maxspeed;
    }

    @Override
    public String toString() {
        return "EdgeRoutenetwork{" +
                "start=" + start +
                ", end=" + end +
                ", coordinateArray=" + Arrays.toString(Arraycoordinates) +
                ", maxspeed=" + maxspeed +
                ", length=" + weight +
                '}';
    }

    //code to compute the length of edges
    private static double convertdtor(double degree) {
        return degree * (Math.PI/180);
    }
    private static double distancehaversine(double lon1, double lat1, double lon2, double lat2){
        //compute the distance between two points given via latitude and longitude in kilometers with the Haversine formula https://en.wikipedia.org/wiki/Haversine_formula

        double Radio = 6371;
        double rLat = convertdtor(lat2-lat1);
        double rLon = convertdtor(lon2-lon1);
        double a =
                Math.sin(rLat/2) * Math.sin(rLat/2) + Math.cos(convertdtor(lat1)) * Math.cos(convertdtor(lat2)) *
                                Math.sin(rLon/2) * Math.sin(rLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = Radio * c;
        return d;
    }

    public double getLength(){
        double total_length = 0;
        for(int i = 0; i < this.Arraycoordinates.length - 1; i++){
            double[] point1 = this.Arraycoordinates[i];
            double[] point2 = this.Arraycoordinates[i+1];
            double partial_length = distancehaversine(point1[0], point1[1], point2[0], point2[1]);
            total_length += partial_length;
        }
        return total_length;
    }
}

/**
 * Clase Arista: Tiene dos elementos tipo Nodo, para el inicio y el fin, un Arreglo para guardar las coordenadas
 * de la Arista (pueden ser varias), un entero para la velocidad máxima (aunque no se usa), un double para el peso,
 * dos constructores: Uno que solo usa los dos nodos y otros que igual tiene la velocidad máxima y las coordenadas
 * de los nodos. Métodos para asentar u obtener el primer nodo, el segundo, las coordenadas o la velocidad máxima
 * Método para imprimir. Mismas dos funciones que en nodo para calcular la distancia.
 * Función que recorre todas las coordenadas de los puntos que conforman la arista, usa la función de Haversine
 * para calcular las distancias, y las va adhiriendo hasta tener una total
 */