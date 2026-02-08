package org.example;

import java.util.Arrays;

public class Geometry {
    public String type;
    public double[][] coordinates;
    public Property properties;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geometry that = (Geometry) o;
        return type.equals(that.type) && Arrays.equals(coordinates, that.coordinates);
    }

    @Override
    public String toString() {
        return "Geometry_geojson{" +
                "type='" + type + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
                ", properties=" + properties +
                '}';
    }
}
/**
 * Clase Geometry. Variable cadena "Type", arreglo para coordenadas "coordinates", variable tipo Property "Properties"
 *Método equals, si el objeto recibido coincide con este mismo regresa true, si viene vacío o no coincide, false,
 * Si no entró a ninguno, revisa si type es igual al type del objeto que viene, y que coordinates sean igual que
 * las de la clase que viene y regresa igual true o false depende de si cumplió.
 * Imprime
 */
