package org.example;
import org.example.Grafo;
import org.example.Closest;
import org.example.AStar;
import org.example.DijkstraB;
import jakarta.json.JsonObject;
/**
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
*/
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;



public class TCPServer {
    final int port;
    final String routenetwork_path; //path to the Json file of the routenetwork
    final Closest routenetwork; //actual routenetwork to compute shortest path

    /**
     *Entero "port". Una cadena para el path "routenetwork_path". Variable tipo lista de nodos "routenetwork".
     */

    public TCPServer(int port, String routenetwork_path) {
        this.port = port;
        this.routenetwork_path = routenetwork_path;
        this.routenetwork = Grafo.readRoadNetwork(this.routenetwork_path, true);
    }

    /**
     *Constructor: Recibe entero y cadena e instancia ambas y luego instacia igual una lista de nodos
     */
    public TCPServer(String routenetwork_path) {  //constructor with default port at 1234
        this.routenetwork_path = routenetwork_path;
        this.port = 1234;
        this.routenetwork = Grafo.readRoadNetwork(this.routenetwork_path, true);
    }

    /** Otro constructor con el puerto por default
     *
     */
    //take the port as argument and read the path to the json file with the routenetwork from command prompt.
    //If no input is given, use default path

    public static TCPServer RoutingServer_readGraphPath(int port){
        System.out.println("Please input path to the file containing the graph:");
        Scanner inputReader = new Scanner(System.in);
        String input = inputReader.nextLine();
        if (input.equals("")){
            System.out.println("No input, using default path");
            input = "C:/Users/juanr/MavenJRRB/src/main/java/org/example/schleswig-holstein.json";
        }
        System.out.println("Using path: " + input);

        return new TCPServer(port, input);
    }

    /** Regresa un objeto de TCPServer, recibe el numero del puerto, pide la ruta donde está guardado
     * el archivo. Lee. Si da un vacío, usa el path a Schleswig Holstein. Imprime que va a usar el path tal.
     * Regresa un objeto del tipo de la clase con los valores de puerto y de path.
     */
    //take RoutingRequest object for the TCPserver and use the respective algorithm to compute the shortest route
    private JsonObject call_routingAlgorithm(RoutingRequest request){
        AStar routing_algo = null;
        //select routing algorithms depending on the request type in the RoutingRequest_TCPserver object
        if (request.request_type.equals("Dijkstra")){
            routing_algo = new DijkstraB(this.routenetwork);

        }
        else if (request.request_type.equals("A-star")){
            routing_algo = new AStar(this.routenetwork, "Haversine");
        }
        else{
            throw new RuntimeException("Routing algorithm must be contained in " + RoutingRequest.possibleRequestTypes);
        }

        JsonObject route = routing_algo.find_shortest_Path_anyLocation_returnLinestring(  //actually find shortest route
                request.latitude_start,
                request.longitude_start,
                request.latitude_target,
                request.longitude_target);

        return route;
    }

    /**Método que recibe un objeto tipo RoutingRequest y regresa un Json, si pidieron Dijkstra instancia un objeto
     * tipo cadena de nodos y lo iguala al output del algoritmo. Si es a-star mismo. Si no está, manda mensaje
     * de que tiene que estar en las opciones. Llama metodo que regresa el Json y le pasa las coordenadas.
     * En routing_algo está configurado ya si va a ser Dijkstra o AStar y llama al método que calcula la ruta mas
     * corta y convierte a Json.
     */
    private class RoutingThread extends Thread{
        //Sub-class to perform routing in different threads
        Socket clientSocket; //socket to connect to the client
        TCPServer server; //current server instance, which saves the port, the path to the current routenetwork and the current routenetwork

        private RoutingThread(TCPServer server, Socket clientSocket) {
            this.clientSocket = clientSocket;
            this.server = server;
        }

        /**
         * Clase que hereda de Thread, define e incluye contructor para el Socke y para el Server.
         */

        @Override
        public void run(){

            System.out.println("Running thread "+ this);
            //get input stream of client
            InputStream is = null;
            try {
                is = clientSocket.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //get output stream of client
            OutputStream os = null;
            try {
                os = clientSocket.getOutputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //convert input and output streams of clients into object streams
            ObjectOutputStream object_os = null;
            try {
                object_os = new ObjectOutputStream(os);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ObjectInputStream object_is = null;
            try {
                object_is = new ObjectInputStream(is);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            RoutingRequest routing_request = null;
            try {
                routing_request = (RoutingRequest) object_is.readObject();  //read routing request from object stream
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            JsonObject result = server.call_routingAlgorithm(routing_request);  //take the request and compute the corresponding route
            try {
                object_os.writeObject(result.toString());  //write the result to the object output stream as a String
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //close input and output streams
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                os.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //close client socket (each client can send only one request)
            try {
                this.clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Ended thread "+ this);

        }

    }

    /**
     * Trata de recibir información del cliente y hace control de errores. Trata de recibir output del cliente
     * y hace gestión de errores. Instancia input y output variable, si no encuentra la petición hace control
     * de erroes. Trata de ejecutar la petición y de escribir el output
     * . Cierra el input. Cierra el output. Cierra el Socket. Manda un mensaje de que se cerró el hilo.
     */
    public void setupServer() throws IOException, ClassNotFoundException {

        try (ServerSocket serverSocket = new ServerSocket(this.port)) {   //create new socket
            System.out.println("Started server at socket "+serverSocket);
            while (true) {  //accept clients as long as the server runs

                Socket clientSocket = serverSocket.accept();  //accept client
                System.out.println("Accepted client at " + clientSocket);

                RoutingThread route_thread = new RoutingThread(this, clientSocket);  //create a new thread that is based on the current TCP server and computes the given routing request and writes the resulting Json object as a String to the object output stream
                route_thread.start();

            }
        }

    }

    /** Trata de montar el Socket, manda mensaje de que se montó, Mientras corra el servidor, Se aceptan
     * acercamientos del cliente, Se imprime que se aceptó, se instancia un hilo y de le pasan los parámetros.
     */


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TCPServer server = RoutingServer_readGraphPath(1234);
        server.setupServer();
    }
/**
 * Se llama e inicializa el Server
 */
}
