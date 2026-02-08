package org.example;

public class Features{
    public String type;
    public Geometry geometry;
    public Property properties;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Features that = (Features) o;
        return type.equals(that.type) && geometry.equals(that.geometry);
    }

}

/**
 * Clase Features, tiene una cadena "Type", un objeto Geometry "Geometry" y un objeto Properties "Property".
 * Equals que recibe un objeto, si es igual que este mismo, regresa verdadero, si el objeto es vacío regresa false,
 * Iguala su variable Features a las de lo que recibe el equals.
 * Regresa verdadero si el type es igual que el recibido y el geometry igual
 */
