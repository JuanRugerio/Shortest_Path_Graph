package org.example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RoutingRequest implements Serializable {
    //objects of this class should be sent to the TCP server to request a route

    public static Set<String> possibleRequestTypes = new HashSet<>(Arrays.asList("Dijkstra", "A-star"));  //all possible request types the TCP server can handle
    public String request_type;
    public final double latitude_start;
    public final double longitude_start;

    public final double latitude_target;
    public final double longitude_target;

    /**Clase que implementa Serializable. Instancia un Set de cadena "possible RequestTypes" con las posibilidades
     * para las peticiones:
     * las opciones son Dijkstra y A-star. Una cadena "request type". doubles para las 4 coordenadas.
     */
    public RoutingRequest(double latitude_start, double longitude_start, double latitude_target, double longitude_target, String request_type) {
        this.latitude_start = latitude_start;
        this.longitude_start = longitude_start;
        this.latitude_target = latitude_target;
        this.longitude_target = longitude_target;

        if (!possibleRequestTypes.contains(request_type)){
            throw new RuntimeException("request_type must be contained in " + possibleRequestTypes);
        }
        else{
            this.request_type = request_type;
        }
    }
    /**Passes the coordinates and initializes them. Then, if the request is not contained in the options
     * it asks for a new one, if it is, ok then.
     *
     */

}
