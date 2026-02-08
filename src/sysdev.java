package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;

import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;
import org.example.Grafo;
import org.example.Closest;
import org.example.AStar;



@Path("sysdev")

public class sysdev {
    private static final String OPENROUTESERVICE_URL = "https://api.openrouteservice.org/v2/directions/driving-car";
    private static final String OPENROUTESERVICE_URL1 = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";
    private static final String OPENROUTESERVICE_KEY = "5b3ce3597851110001cf624831ece868a7bc4e4aa660a9a97860c8b7";

    @GET
    @Path("orsdirections")
    @Produces(MediaType.APPLICATION_JSON)

    public JsonObject requestroute(@QueryParam("originLat") double originLat, @QueryParam("originLon") double originLon,
                                   @QueryParam("destinationLat") double destinationLat, @QueryParam("destinationLon") double destinationLon) {
        String start = originLon + "," + originLat;
        String end = destinationLon + "," + destinationLat;
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

        return jsonObject;
    }

    @POST
    @Path("orsdirections")

    public static JsonObject requestroutep(Jsonj request) throws IOException {
        Jsonj receivedJson = request;
        final JsonObject request1 = Json.createObjectBuilder()
                .add("coordinates",
                        Json.createArrayBuilder()
                                .add(
                                        Json.createArrayBuilder()
                                                .add(receivedJson.originLon)
                                                .add(receivedJson.originLat)
                                                .build()
                                )
                                .add(
                                        Json.createArrayBuilder()
                                                .add(receivedJson.destinationLon)
                                                .add(receivedJson.destinationLat)
                                                .build()
                                )
                                .build()
                )
                .build();

        final JerseyClient client1 = new JerseyClientBuilder().build();
        final JerseyWebTarget webTarget = client1.target(OPENROUTESERVICE_URL1);

        final Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.WILDCARD)
                .header("Authorization", OPENROUTESERVICE_KEY) // send the API key for authentication
                .post(Entity.json(request1));

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
        } else {
            // get the JSON response
            final String responseString = response.readEntity(String.class);
            final JsonObject jsonObject = Json.createReader(new StringReader(responseString)).readObject();
            return jsonObject;
        }

    }





    }


/**
 * GET: As you hit Go in the UI, it will parse the coordinates for the selected points. Everything else is executed in
 * the exact same way as in Assignment 1 and the resulting GEOJSON is returned to the UI for it to be displayed
 */

/**
 * POST: You are told the UI will send you a JSON, so you build a class that is capable of receiving the 4 coordinates
 * as will be sent. Then, you will have to send a JSON to the Openroute API for your POST request, so you build it,
 * it will have first an Array that will have 2 spaces, each of those with 2 spaces, to hold both pairs of
 * coordinates. Then you target not the previous route, but the one with json at the end. You send the json accept
 * the one that comes back and that will be taken by the UI to display
 */
