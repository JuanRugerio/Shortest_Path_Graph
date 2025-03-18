package org.example;

import org.example.Closest;
import org.example.Nodo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import java.io.StringReader;
import java.util.*;
import java.util.function.BiFunction;

public class AStar implements Comparator<Nodo> {

    private final Closest network;   //roadnetwork to perform A* on

    //heuristic function which takes the target node and a current node and returns the value of the heuristic
    BiFunction<Nodo, Nodo, Double> heuristic;

    // For node n, fScore[n]= gScore[Node] + h(Node). fScore[n] represents our current best guess as to
    // how cheap a path could be from start to finish if it goes through Node n.
    private final HashMap<Nodo, Double> f_score_map; //score f_scores for each node in this Map

    // For node n, gScore[Node] is the cost of the cheapest path from start to n currently known.
    private final HashMap<Nodo, Double> g_score_map; //score g
    // _scores for each node in this Map

    public BiFunction<Nodo, Nodo, Double> getHeuristic_function() {
        return heuristic;
    }

    public void setHeuristic_function(BiFunction<Nodo, Nodo, Double> heuristic_function) {
        this.heuristic = heuristic_function;
    }

    public AStar(Closest network, BiFunction<Nodo, Nodo, Double> heuristic_function){
        this.network = network;
        this.heuristic = heuristic_function;
        this.f_score_map =  new HashMap();
        this.g_score_map = new HashMap();
    }

    //A* algorithm, where the heuristic can be given by a String keyword, which can be "none" or "Haversine"
    public AStar(Closest network, String predefined_heuristic){
        this.network = network;
        this.f_score_map =  new HashMap();
        this.g_score_map = new HashMap();

        if (predefined_heuristic.equals("none")){
            //A-star is equal to Dijkstra's algorithm in this case
            this.heuristic = (Nodo target_node, Nodo n2) -> {return (0.0);};
        } else if (predefined_heuristic.equals("Haversine")) {
            this.heuristic = (Nodo target_node, Nodo n2)
                    -> {return (Nodo.get_Haversine_distance(target_node, n2));};
        }
        else {
            throw new IllegalArgumentException("This heuristic is not implemented");
        }
    }

    //constructor with Haversine distance as default heuristic
    public AStar(Closest network){
        this(network, "Haversine");
    }

    @Override
    public int compare(Nodo n1, Nodo n2) {
        //Use the f-scores of the nodes to compare them in order to determine the next node to visit
        //used to maintain order in the priority queue
        double f_score_n1 = this.f_score_map.getOrDefault(n1, Double.POSITIVE_INFINITY);
        double f_score_n2 = this.f_score_map.getOrDefault(n2, Double.POSITIVE_INFINITY);

        return Double.compare(f_score_n1, f_score_n2);
    }

    private List<Nodo> extract_path(Nodo start, Nodo end, HashMap<Nodo, Nodo> camFrom){
        // find path from start node to end node using the output of A*.

        List<Nodo> total_path = new LinkedList<>();  //List to save path from start to end
        Nodo current = end;   //begin with end node
        total_path.add(end);
        while (current != start){  //while the start was not reached ...
            current = camFrom.get(current);   //... get the node from where the previous one was entered
            total_path.add(current);   //add it to the list
        }


        Collections.reverse(total_path);   //reverse total path and return it
        return total_path;
    }

    public List<Nodo> find_shortest_Path(Nodo start_node, Nodo target_node){

        PriorityQueue<Nodo> open_set = new PriorityQueue(1, this);  //create empty priority queue which uses the compare function of this class
        open_set.add(start_node); // add start node to queue

        HashMap<Nodo, Nodo> cameFrom = new HashMap();

        f_score_map.put(start_node, this.heuristic.apply(target_node, start_node));
        g_score_map.put(start_node, 0.0);


        while(!open_set.isEmpty()){
            Nodo current = open_set.poll();  //get first element of queue and remove it

            if(current == target_node){  //if the target has been reached, return the path from start to target via the extract path method
                return extract_path(start_node, target_node, cameFrom);
            }
            for (Nodo adj_node : current.getAdjacentNodes()){

                double graph_distance = Nodo.get_Haversine_distance(adj_node, current);
                double tentative_gScore = this.g_score_map.getOrDefault(current, Double.POSITIVE_INFINITY) + graph_distance;

                if (tentative_gScore < this.g_score_map.getOrDefault(adj_node, Double.POSITIVE_INFINITY)){
                    // This path to neighbor is better than any previous one.
                    cameFrom.put(adj_node, current);
                    this.g_score_map.put(adj_node, tentative_gScore);
                    this.f_score_map.put(adj_node, tentative_gScore + this.heuristic.apply(target_node, adj_node));

                    if (!open_set.contains(adj_node)) {
                        open_set.add(adj_node);  // add node to the list of nodes to still visit if it is not already there
                    }

                }

            }

        }
        System.out.println("No path found");
        return null;
    }

    public List<Nodo> find_shortest_Path_anyLocation(double latitude_start, double longitude_start, double latitude_target, double longitude_target){
        Nodo start_node = network.closestNode(latitude_start, longitude_start);
        Nodo target_node = network.closestNode(latitude_target, longitude_target);

        return find_shortest_Path(start_node, target_node);
    }

    // convert output of the A-star algorithm into a GeoJson Format
    public static JsonObject convert_A_star_output_to_GeoJson_jakarta(List<Nodo> A_star_output){

        JsonArrayBuilder json_array = Json.createArrayBuilder();  //create JsonArray to take the coordinates of the nodes of the path
        for (Nodo n: A_star_output){                  //iterate over all nodes in the A_star_output and add them to the json array
            JsonArray coordinate_arr = Json.createArrayBuilder().
                    add(n.getLongitude()).
                    add(n.getLatitude()).build();
            json_array.add(coordinate_arr);
        }
        JsonArray json_array_built = json_array.build();

        //create Linestring Json object
        JsonObject astar_route = Json.createObjectBuilder()
                .add("type", "LineString")
                .add("coordinates", json_array_built).build();

        //create fina featureCollection object that contains the LineString
        JsonObject a_star_route_feature_collection = Json.createObjectBuilder()
                .add("type", "FeatureCollection")
                .add("features",
                        Json.createArrayBuilder()
                                .add(
                                        Json.createObjectBuilder()
                                                .add("type", "Feature")
                                                .add("properties", Json.createObjectBuilder().build())
                                                .add("geometry", astar_route).build()
                                ).build()

                ).build();

        return  a_star_route_feature_collection;
    }

    public static JsonObject convert_A_star_output_to_GeoJson(List<Nodo> A_star_output){
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode route_array = mapper.createArrayNode();        //create JsonArray to take the coordinates of the nodes of the path
        for (Nodo n: A_star_output){                  //iterate over all nodes in the A_star_output and add them to the json array
            ArrayNode coordinate_arr = mapper.createArrayNode().
                    add(n.getLongitude()).
                    add(n.getLatitude());
            route_array.add(coordinate_arr);
        }

        ObjectNode astar_route = mapper.createObjectNode();
        astar_route.put("type", "LineString");
        astar_route.set("coordinates", route_array);

        ObjectNode linestring_route = mapper.createObjectNode();
        linestring_route.put("type", "Feature");
        linestring_route.set("properties", mapper.createObjectNode());
        linestring_route.set("geometry", astar_route);


        ObjectNode a_star_route_feature_collection = mapper.createObjectNode()
                .put("type", "FeatureCollection")
                .set("features",
                        mapper.createArrayNode()
                                .add(
                                        linestring_route
                                )

                );

        JsonObject returnGeoJson = Json.createReader(new StringReader(a_star_route_feature_collection.toString())).readObject();
        return returnGeoJson;
    }

    public JsonObject find_shortest_Path_anyLocation_returnLinestring(double latitude_start, double longitude_start, double latitude_target, double longitude_target){
        List<Nodo> result_astar = find_shortest_Path_anyLocation(latitude_start, longitude_start, latitude_target, longitude_target);

        return convert_A_star_output_to_GeoJson(result_astar);
    }

}

/**
 *
 */