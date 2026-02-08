package de.lmu.ifi.dbs.sysdev.openrouteservice;


import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;

import java.io.StringReader;


public class RequestElevation {

    private static final String OPENROUTESERVICE_URL = "https://api.openrouteservice.org/elevation/point";
    private static final String OPENROUTESERVICE_KEY = "5b3ce3597851110001cf624831ece868a7bc4e4aa660a9a97860c8b7";

    public static void poiSearch(double lat, double lon) {
        // create a json object which we will send in the post request
        // {
        //      format_in: "point",
        //      geometry: [lon, lat]
        // }
        final JsonObject request = Json.createObjectBuilder()
                .add("format_in", "point")
                .add(
                        "geometry",
                        Json.createArrayBuilder()
                                .add(lon)
                                .add(lat)
                                .build()
                )
                .build();

        // use the jersey client api to make HTTP requests
        final JerseyClient client = new JerseyClientBuilder().build();
        final JerseyWebTarget webTarget = client.target(OPENROUTESERVICE_URL);
        final Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", OPENROUTESERVICE_KEY) // send the API key for authentication
                .post(Entity.json(request));

        // check the result
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
        }

        // get the JSON response
        final String responseString = response.readEntity(String.class);
        final JsonObject jsonObject = Json.createReader(new StringReader(responseString)).readObject();
        System.out.println("Response: " + jsonObject);

        // Extract the elevation
        final JsonNumber elevation = jsonObject
                .getJsonObject("geometry")
                .getJsonArray("coordinates")
                .getJsonNumber(2);
        System.out.println("Elevation: " + elevation.doubleValue() + "m");
    }

    public static void main(String[] args) {
        poiSearch(48.1499, 11.5942);
    }
}

/**Importa herramientas de lo que tiene en el POM. Declara clase RequestElevation.
 * En una variable guardas el URL de la API y en otra tu clave individual.
 * Creas una función PoiSearch, que recibe dos doubles, uno para latitud y otro para longitud.
 * Instancias un objeto Json que se llama request. Le metes un campo que se llama "format-in" y que guarda una
 * cadena de "point" y otra variable que se llama "Geometry" y va a tener un arreglo, y en ese arreglo van a haber
 * dos números: longitud y latitud.  Instancias un cliente con Jersey. Instancias una variable tipo objetivo y le
 * pasas la URL. Defines una variable respuesta, Haces la petición del JSON, la aceptas y te identificas con tu clave
 * y mandas el JSON que creaste. Hace una revisión de que sea OK el estatus que regresa Openroute y si
 * no, regresa la clave. Toma la respuesta, la lee y la pone en un String. Crea un JSON y usando la función de leer
 * lee la cadena y la convierte a JSON, lo imprime. Declara una variable tipo JSON number, para extraer la elevación
 * se mete a la variable Geometry del objeto, se mete a la variable coordinates de ese Arreglo y saca la segunda
 * coordenada, e imprime. En el main solamente llama a la función de la clase.
 * */
