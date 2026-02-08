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
import java.util.Scanner;

public class RequestRoute {
    public static void main(String[] args) {

        // Get Start Point
        Scanner scanStartLat = new Scanner(System.in);
        System.out.println("Enter Start Latitude");
        String startLat = scanStartLat.nextLine();
        Scanner scanStartLon = new Scanner(System.in);
        System.out.println("Enter Start Longitude");
        String startLon = scanStartLon.nextLine();
        String start = startLat + "," + startLon; // Format String

        //Get End Point
        Scanner scanEndLat = new Scanner(System.in);
        System.out.println("Enter End Latitude");
        String endLat = scanEndLat.nextLine();
        Scanner scanEndLon = new Scanner(System.in);
        System.out.println("Enter End Longitude");
        String endLon = scanEndLon.nextLine();
        String end = endLat + "," + endLon; // Format String


        // https://openrouteservice.org/dev/#/api-docs/v2/directions/{profile}/get
        String key = "5b3ce3597851110001cf6248aa5a2f545e11431d948fc5566c0d1187";
        String url = "https://api.openrouteservice.org/v2/directions/driving-car"; // Build HTTPS request
        final JerseyClient client = new JerseyClientBuilder().build();
        final JerseyWebTarget webTarget = client.target(url);
        final Response response = webTarget
                .queryParam("api_key", key)
                .queryParam("start", start)
                .queryParam("end", end)
                .request()
                //.header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .get();

        // result
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
        }

        // Print GeoJSON
        final String responseString = response.readEntity(String.class);
        final JsonObject jsonObject = Json.createReader(new StringReader(responseString)).readObject();
        System.out.println("GeoJSON: " + jsonObject);

    }
}

