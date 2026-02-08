package org.example;
import org.example.Closest;
import org.example.Grafo;
import org.example.DijkstraB;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;

@Path("sysdev/dijkstra_direct")
public class Dijkstra_Resource {
    @GET
    public JsonObject getRoute(@QueryParam("originLat") double originLat,
                               @QueryParam("originLon") double originLon,
                               @QueryParam("destinationLat") double destinationLat,
                               @QueryParam("destinationLon") double destinationLon) throws Exception {

        String myPath = "C:/Users/juanr/MavenJRRB/src/main/java/org/example/schleswig-holstein.json";
        Closest network_sh = Grafo.readRoadNetwork(myPath, false);

        DijkstraB dijkstra = new DijkstraB(network_sh);
        JsonObject route = dijkstra.find_shortest_Path_anyLocation_returnLinestring(
                originLat,
                originLon,
                destinationLat,
                destinationLon);

        return route;
    }





}
