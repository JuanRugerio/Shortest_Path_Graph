package org.example;

import org.example.FeatureCollection;
import org.example.Features;
import org.example.Geometry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.nonNull;

public class Grafo {


    public static Closest readRoadNetwork(String path, Boolean skip_nodes){
        // read the roadnetwork given at the path as a geojson and return it converted to a NetworkRoutenetwork
        //if skip_nodes is True, only read the nodes from the edges in the Json

        // Read in the geojson
        ObjectMapper mapper = new ObjectMapper();
        FeatureCollection Data;
        try {
            Data = mapper.readValue(new File(path),  new TypeReference<FeatureCollection>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        List<Nodo> Listofnodes = new ArrayList<>();  //create empty lists for the nodes of the routenetwork
        HashMap<String, Nodo> coordinates2Node = new HashMap<>();  //map the name/String representation based on longitude and latitude  of the node to itself

        for(Features feat: Data.features){       //iterate over the feature objects of the feature collection
            if(feat.geometry.type.equals("MultiPoint") && (!skip_nodes)){       //if the feature is a Multipoint object, iterate over all its points and create corresponding Node Objects
                for(double[] point:(feat.geometry.coordinates)){
                    Nodo node = new Nodo(point[0], point[1]);
                    Listofnodes.add(node);   //add node to node list

                    coordinates2Node.put(node.toString(), node);   //add node with its representation to this Map to find it fast later
                }
            }
            if(feat.geometry.type.equals("LineString")){       //if the feature is a Multipoint object, iterate over all its points and connect corresponding Node Objects
                Geometry linestring = feat.geometry;

                for(int i = 0; i < linestring.coordinates.length - 1; i++){  //iterate over all the nodes/edges in that particular linestring
                    double[] start = linestring.coordinates[i];
                    double[] end = linestring.coordinates[i+1];

                    double lon_start = start[0];
                    double lat_start = start[1];

                    double lon_end = end[0];
                    double lat_end = end[1];

                    //find start and end nodes for the coordinates via the coordinates2Node HashMap (instead of e.g. iterating over the node list)
                    Nodo start_node = coordinates2Node.getOrDefault("Nodo{" +
                            "longitude=" + lon_start +
                            ", latitude=" + lat_start +
                            '}', null);
                    Nodo end_node = coordinates2Node.getOrDefault("Nodo{" +
                            "longitude=" + lon_end +
                            ", latitude=" + lat_end +
                            '}', null);

                    //In case the node in the lineString was not prevoiously introduced:
                    if (nonNull(start_node) == false){
                        start_node = new Nodo(lon_start, lat_start);  //create new node
                        Listofnodes.add(start_node);  //add it to the node_list
                        coordinates2Node.put(start_node.toString(), start_node); //add the node to the hash map

                    }
                    if (nonNull(end_node) == false){
                        end_node = new Nodo(lon_end, lat_end);  //create new node
                        Listofnodes.add(end_node);  //add it to the node_list
                        coordinates2Node.put(end_node.toString(), end_node);  //add the node to the hash map
                    }

                    start_node.addAdjacentNode(end_node);  //make the end node adjacent to the end node
                }




            }
        }
        Closest result = new Closest(Listofnodes);

        return result;
    }


}

/**
 * Clase Grafo. Función "ReadroadNetwork" que regresa una lista de nodos, recibe una cadena con la ubicación de
 * lo que va a leer y un booleano "Skip nodes". Instancía un Objectmapper y una FeatureCollection,
 * intenta lees y almacenar los Features y hace gestión de errores. Instancia un arreglo de listas tipo
 * objeto nodo. Instancia un mapeo de cadenas a nodos para relacionar coordenadas con nodos,
 *
 * Hace un For que va circulando por la colección de Features de lo que recibió,
 * Si es un multipoint, recorre todos sus nodos, toma las coordenadas, las almacena en un nodo y lo adhiere a
 * una lista de nodos, agrega el nodo al Mapping.
 *
 * Si es un LineString, instancia clase Geometry llamada linestring del feature en una variable, hace un for, va recorriendo las coordenadas
 * de par en par y las va guardando en start y en end, guarda en variables separadas la latitud y la longitud,
 *con el Map, encuentra el nodo de inicio y de fin de la arista, si en los LineStrings ecuentra un nodo que
 * aún no había salido en los Multipoints, lo crea y ahiere a la lista de nodos y al mapping. Lo mismo para el
 * nodo final, al final adhiere a la lista de adyacencia del primer nodo, el segundo.
 * Instancia una variable de tipo CLosest con la lista de nodos. Y regresa el objeto
 *
 */
