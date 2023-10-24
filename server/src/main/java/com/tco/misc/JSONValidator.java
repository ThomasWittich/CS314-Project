 package com.tco.misc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import org.everit.json.schema.SchemaException;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.ValidationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JSONValidator {

    private static final Logger log = LoggerFactory.getLogger(JSONValidator.class);

    private JSONValidator() {}

    public static void validate(String requestBody, Type requestType) throws IOException {
        Schema schema = getSchema(requestType.getTypeName());
        try {
            JSONObject request = new JSONObject(requestBody);
            log.info("JSON VALIDATOR REQUEST BODY: {}",requestBody);
            schema.validate(request);
        } catch (ValidationException e) {
            throw new IOException(e.getMessage());
        }
    }

    private static Schema getSchema(String className) throws IOException {

        log.info("Get Schema for Class: {}",className);

        String schemaPath = String.format("/schemas/%s.json", className.substring(className.lastIndexOf(".") + 1));
        try (InputStream inputStream = JSONValidator.class.getResourceAsStream(schemaPath)) {
            log.info("PASS GET Schema for Class: {}",className);
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            return SchemaLoader.load(rawSchema);
        } catch (NullPointerException e) {
            throw new IOException(String.format("No schema %s exists for type %s", schemaPath, className));
        } catch (SchemaException | JSONException e) {
            throw new IOException(String.format("Invalid schema format. Root message: %s", e.getMessage()));
        }
    }
}
