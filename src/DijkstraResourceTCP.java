package org.example;

import org.example.Grafo;
import org.example.Closest;
import org.example.AStar;
import org.example.RoutingRequest;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.*;

import java.io.*;
import java.net.Socket;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("sysdev/dijkstra")
public class DijkstraResourceTCP {

    /**
     * Let result at required path for UI to take
     */
    @GET
    public JsonObject getRoute(@QueryParam("originLat") double originLat,
                               @QueryParam("originLon") double originLon,
                               @QueryParam("destinationLat") double destinationLat,
                               @QueryParam("destinationLon") double destinationLon) throws Exception {
/**
 * Takes the coordinates from UI, will return a Json
 */

        try (Socket socket = new Socket("localhost", 1234)) {  //create new socket
            OutputStream os = socket.getOutputStream();  //new outputStream for the socket
            InputStream is = socket.getInputStream();  //as well as a new input stream

            ObjectOutputStream object_os = new ObjectOutputStream(os);  //convert those Streams into object streams to send objects
            ObjectInputStream object_is = new ObjectInputStream(is);

            RoutingRequest req = new RoutingRequest(originLat, originLon, destinationLat, destinationLon, "Dijkstra"); //create routing request object
            object_os.writeObject(req); //write this object to the output stream

            String route_res_str = (String) object_is.readObject();  //read routing request from object stream

            JsonReader jsonReader = Json.createReader(new StringReader(route_res_str)); //convert the read string back into a Json object
            JsonObject route_res_json = jsonReader.readObject();

            object_os.close(); //close outout and input streams
            is.close();

            return route_res_json;
        }
/**
 * Creates Socket, input and output streams, instantiates a Routing Request, passes the coordinates and that the
 * method is Dijkstra. Reads as String then turns into json, returns json
 */

    }





}
