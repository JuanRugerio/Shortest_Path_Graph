package org.example;

import org.example.FeatureCollection;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Test_Closest {
    public static void main(String[] args){
        ObjectMapper mapper = new ObjectMapper();
        String myPath = "C:/Users/juanr/MavenJRRB/src/main/java/org/example/schleswig-holstein.json";

        FeatureCollection network_raw;
        try {
            network_raw = mapper.readValue(new File(myPath),  new TypeReference<FeatureCollection>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println((network_raw.features[0].geometry.coordinates[0][0]));
    }
}
/**
 * Clase Test_Closest: Tiene un main, crea un object mapper, una variable de cadena con la ruta desde
 * C hasta donde está tu archivo de schleswig holstein,instancia una Feature Collection,
 * Iguala la variable de Feature Collection a la lectura, a la cuál le pasa la ruta hasta el archivo y
 * y la referencia a el Feature Collection, hace un control de errores, Hace una impresión para ver si funcionó
 *
 */