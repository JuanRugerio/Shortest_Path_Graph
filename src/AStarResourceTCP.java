package org.example;

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
@Path("sysdev/astar")
public class AStarResourceTCP {

    /**
     * Method handling HTTP GET requests. For a shortest path query.
     * The route is calculated via the Astar algorithm
     *
     * @param destinationLat   Latitude of destination
     * @param destinationLon   Longitude of destination
     * @param originLat        Latitude of destination
     * @param originLon        Longitude of destination
     * @return JsonObject of the corresponding path
     */
    @GET
    public JsonObject getRoute(@QueryParam("originLat") double originLat,
                               @QueryParam("originLon") double originLon,
                               @QueryParam("destinationLat") double destinationLat,
                               @QueryParam("destinationLon") double destinationLon) throws Exception {


        try (Socket socket = new Socket("localhost", 1234)) {  //create new socket
            OutputStream os = socket.getOutputStream();   //new outputStream for the socket
            InputStream is = socket.getInputStream();     //as well as a new input stream

            ObjectOutputStream object_os = new ObjectOutputStream(os);  //convert those Streams into object streams to send objects
            ObjectInputStream object_is = new ObjectInputStream(is);

            RoutingRequest req = new RoutingRequest(originLat, originLon, destinationLat, destinationLon, "A-star");  //create routing request object
            object_os.writeObject(req);  //write this object to the output stream

            String route_res_str = (String) object_is.readObject();  //read routing request from object stream as a string

            JsonReader jsonReader = Json.createReader(new StringReader(route_res_str));  //convert the read string back into a Json object
            JsonObject route_res_json = jsonReader.readObject();

            object_os.close();  //close outout and input streams
            is.close();

            return route_res_json;
        }


    }





}
