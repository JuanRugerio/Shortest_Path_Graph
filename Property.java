package org.example;

public class Property {
    public int maxspeed;

    public Property(int maxspeed) {
        this.maxspeed = maxspeed;
    }
    public Property() {
        this.maxspeed = -1;
    }

    @Override
    public String toString() {
        return "Property{" +
                "maxspeed=" + maxspeed +
                '}';
    }
}

/**
 * Clase Property. Tiene un entero "Maxspeed". Tiene un constructor para el. Un método que lo inicializa a -1.
 * Impresión.
 */