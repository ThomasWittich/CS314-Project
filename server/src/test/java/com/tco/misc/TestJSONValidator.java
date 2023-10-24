package com.tco.misc;

import com.tco.requests.ConfigRequest;
import com.tco.requests.DistancesRequest;
import java.lang.reflect.Type;

import org.everit.json.schema.SchemaException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TestJSONValidator {

    private void test(String request, Type type, boolean valid) {
        try {
            JSONValidator.validate(request, type);
            assertTrue(valid);
        } catch ( Exception e ) {
            assertFalse(valid);
        }
    }

    @Test
    @DisplayName("base: Config request should fail schema validation")
    public void testConfigRequestFail() {
        test("{}", ConfigRequest.class, false);
    }

    @Test
    @DisplayName("xander3: Distances request should fail schema validation")
    public void testDistancesRequestFail() {
        test("{}", DistancesRequest.class, false);
    }

    @Test
    @DisplayName("base: Config request should pass schema validation")
    public void testConfigRequestPass() {
        test("{\"requestType\":\"config\","+
            "\"features\":[\"config\"]}", ConfigRequest.class, true);
    }

    @Test
    @DisplayName("xander3: Distances request should pass schema validation")
    public void testDistancesRequestPass() {
        test("{\"requestType\":\"distances\",\"earthRadius\": 1.0, \"places\":[{\"name\": \"Seattle, WA\", \"latitude\":  \"47.6062\", \"longitude\": \"122.3321\"}, {\"name\": \"Las Vegas, NV\", \"latitude\":  \"36.1716\", \"longitude\": \"115.1391\"}]}", DistancesRequest.class, true);
    }

    @Test
    @DisplayName("base: There should be no schema for the JSONValidator class")
    public void testMissingSchema() {
        test("", JSONValidator.class, false);
    }

    @Test
    @DisplayName("base: An invalid schema results in validate() failing")
    public void testInvalidSchema() {
        try (MockedStatic<SchemaLoader> mockedSchemaLoader = mockStatic(SchemaLoader.class)) {
            mockedSchemaLoader.when(() -> SchemaLoader.load(any(JSONObject.class)))
                    .thenThrow(SchemaException.class);

            test("{\"requestType\":\"config\"}", ConfigRequest.class, false);
        }
    }
}
