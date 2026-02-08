package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReadSch {
    public double coordinate1;
    public double coordinate2;

    public ReadSch() {
    }

    public double getcoordinate1() {
        return coordinate1;
    }

    public void setcoordinate1(double coordinate1) {
        this.coordinate1 = coordinate1;
    }

    public double getcoordinate2() {
        return coordinate2;
    }

    public void setCoordinate2(double coordinate2) {
        this.coordinate2 = coordinate2;
    }

    @Override
    public String toString() {
        return "ReachSch{" +
                "coordinate1='" + coordinate1 + '\'' +
                ", coordinate2=" + coordinate2 +
                '}';
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse

        List<ReadSch>  ReadSch = mapper.readValue(new File("C:\\Users\\juanr\\MavenJRRB\\src\\main\\java\\org\\example\\schleswig-holstein.json"), new TypeReference<List<ReadSch>>() {});
        /**FriendClass[] FriendClass = mapper.readValue(new File("C:\\Users\\juanr\\MavenJRRB\\src\\main\\java\\org\\example\\test.json"), FriendClass[].class);*/

        System.out.println((ReadSch));

    }
}

/**
 * First imports all it needs. At class declares it uses a string, a value and a list of classes.
 * It also establishes methods to initialize and get the 3 attributes.
 * Then at main,
 * Declares and initializes an ObjectMapper, First sets up the List, then
 * Equals the list "friends" of FriendClass objects to what you map after you read using the mapper,
 * you tell it, you map it to the defined class. Then, you print the list.
 */
