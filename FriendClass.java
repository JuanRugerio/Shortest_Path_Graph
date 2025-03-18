package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FriendClass {
    public String name;
    public int value;
    public List<FriendClass> friends;

    public FriendClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<FriendClass> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendClass> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "FriendClass{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", friends=" + friends +
                '}';
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse

        List<FriendClass>  friends = mapper.readValue(new File("C:\\Users\\juanr\\MavenJRRB\\src\\main\\java\\org\\example\\test.json"), new TypeReference<List<FriendClass>>() {});
        /**FriendClass[] FriendClass = mapper.readValue(new File("C:\\Users\\juanr\\MavenJRRB\\src\\main\\java\\org\\example\\test.json"), FriendClass[].class);*/

        System.out.println((friends));
        System.out.println(friends.get(5));

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