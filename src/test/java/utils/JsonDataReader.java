package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public final class JsonDataReader {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static JsonNode cached;

    private JsonDataReader() {}

    public static JsonNode getTestData() {
        if (cached == null) {
            cached = load("testdata.json");
        }
        return cached;
    }

    private static JsonNode load(String resourcePath) {
        try (InputStream is = JsonDataReader.class
                .getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            return MAPPER.readTree(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load " + resourcePath, e);
        }
    }
}