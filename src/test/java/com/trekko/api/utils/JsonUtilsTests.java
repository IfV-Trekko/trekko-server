package com.trekko.api.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class JsonUtilsTests {

    @Test
    void testParse_validJson() {
        String json = "{\"key\":\"value\"}";
        Map<String, Object> expected = new HashMap<>();
        expected.put("key", "value");

        assertEquals(expected, JsonUtils.parse(json));
    }

    @Test
    void testParse_invalidJson() {
        String json = "invalid json";
        assertNull(JsonUtils.parse(json));
    }

    @Test
    void testStringify_validMap() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "value");
        String expectedJson = "{\"key\":\"value\"}";

        assertEquals(expectedJson, JsonUtils.stringify(map).replaceAll("\\s+", ""));
    }

    @Test
    void testStringify_emptyMap() throws Exception {
        Map<String, Object> map = new HashMap<>();
        String expectedJson = "{}";

        assertEquals(expectedJson, JsonUtils.stringify(map));
    }

    @Test
    void testMerge_nonOverlapping() {
        String json1 = "{\"key1\":\"value1\"}";
        String json2 = "{\"key2\":\"value2\"}";
        String expected = "{\"key1\":\"value1\",\"key2\":\"value2\"}";

        assertEquals(expected, JsonUtils.merge(json1, json2).replaceAll("\\s+", ""));
    }

    @Test
    void testMerge_overlapping() {
        String json1 = "{\"key\":\"value1\"}";
        String json2 = "{\"key\":\"value2\"}";
        String expected = "{\"key\":\"value2\"}";

        assertEquals(expected, JsonUtils.merge(json1, json2).replaceAll("\\s+", ""));
    }
}
