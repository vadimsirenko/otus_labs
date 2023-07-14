package ru.vasire.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ObjectSerializer {
    public static String writeValueAsString(ObjectMapper objectMapper, Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T readValue(ObjectMapper objectMapper, String path, Class<T> type) {
        try {
            return objectMapper.readValue(new File(path), type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeValueToFile(ObjectMapper objectMapper, String path, Object object) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
