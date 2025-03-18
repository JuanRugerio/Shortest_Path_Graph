package org.example;


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


public class RequestRouteGood {

    private static final String OPENROUTESERVICE_URL = "https://api.openrouteservice.org/v2/directions/driving-car";
    private static final String OPENROUTESERVICE_KEY = "5b3ce3597851110001cf624831ece868a7bc4e4aa660a9a97860c8b7";

    public static void requestroute(double lat1, double lon1, double lat2, double lon2) {

        // use the jersey client api to make HTTP requests
        String start = lon1 + "," + lat1;
        String end = lon2 + "," + lat2;
        final JerseyClient client = new JerseyClientBuilder().build();
        final JerseyWebTarget webTarget = client.target(OPENROUTESERVICE_URL);
        final Response response = webTarget
                .queryParam("api_key", OPENROUTESERVICE_KEY)
                .queryParam("start", start)
                .queryParam("end", end)
                .request()
                .get();// send the API key for authentication;

        // check the result
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
        }

        // get the JSON response
        final String responseString = response.readEntity(String.class);
        final JsonObject jsonObject = Json.createReader(new StringReader(responseString)).readObject();
        System.out.println("Response: " + jsonObject);
    }

    public static void main(String[] args) {
        requestroute(49.41461, 8.681495, 49.420318, 8.687872);
    }
}
