package org.example;

import org.example.Nodo;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Test_RoutingRequest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket("localhost", 1234)) {
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            ObjectOutputStream object_os = new ObjectOutputStream(os);
            ObjectInputStream object_is = new ObjectInputStream(is);

            RoutingRequest req = new RoutingRequest(1, 1, 1, 1, "Dijkstra");
            object_os.writeObject(req);

            String route_res_str = (String) object_is.readObject();  //read routing request from object stream

            JsonReader jsonReader = Json.createReader(new StringReader(route_res_str));
            JsonObject route_res_json = jsonReader.readObject();

            System.out.println(route_res_json);


            object_os.close();
            is.close();
        }
    }
}
/**
 * Trata de instanciar un Socket. Construye el Output y el InputStream. Llama a la clase que verifica que el request
 * esté dentro de las opciones y almacena las coordenadas involucradas. Mete al objeto output la salida de la clase.
 * Lee el request. Lo pasa a Json. Lo imprime. Cierra los canales.
 */
