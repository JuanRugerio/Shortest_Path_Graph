package org.example;

        import com.fasterxml.jackson.core.type.TypeReference;
        import com.fasterxml.jackson.databind.ObjectMapper;

        import java.io.File;
        import java.io.IOException;
        import java.util.List;

public class Schleswig {
    public String type;
    public List<Features> features;

    public class Features {
        public String type;
        public Geometry geometry;
        public Properties properties;
    }

    public static class Geometry {
        public String type;
        public List<List<Double>> coordinates;
    }

    public static class Properties {
        public Integer maxspeed;
    }


    public Schleswig(){};

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse

        Schleswig  nodes = mapper.readValue(new File("C:\\Users\\juanr\\MavenJRRB\\src\\main\\java\\org\\example\\schleswig-holstein.json"), new TypeReference<Schleswig>() {});
        /**FriendClass[] FriendClass = mapper.readValue(new File("C:\\Users\\juanr\\MavenJRRB\\src\\main\\java\\org\\example\\test.json"), FriendClass[].class);*/

        System.out.println((nodes.features.get(2).geometry));

    }
}