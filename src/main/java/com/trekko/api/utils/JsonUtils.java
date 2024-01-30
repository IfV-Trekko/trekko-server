package com.trekko.api.utils;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    public static Map<String, Object> parse(final String jsonString) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, new TypeReference<Map<String, Object>>() {
            });
        } catch (final JsonProcessingException e) {
            return null;
        }
    }

    public static String merge(final String jsonString1, final String jsonString2) {
        final var jsonMap1 = parse(jsonString1);
        final var jsonMap2 = parse(jsonString2);

        jsonMap1.putAll(jsonMap2);

        return stringify(jsonMap1);
    }

    public static String stringify(final Map<String, Object> jsonMap) {
        try {
            return OBJECT_MAPPER.writeValueAsString(jsonMap);
        } catch (final JsonProcessingException e) {
            return null;
        }
    }
}
