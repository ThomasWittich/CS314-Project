package com.tco.server;

import java.net.ServerSocket;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import spark.Spark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

public class TestMicroServer {

    private static final transient Logger log = LoggerFactory.getLogger(TestMicroServer.class);

    public final static int TEST_SERVER_PORT = getAvailablePort();
    public final static String BASE_URL = "http://localhost:" + TEST_SERVER_PORT;

    private static int getAvailablePort(){
        try ( ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (Exception E) {
            return 8000;
        }
    }

    @BeforeAll
    public static void startTheMicroServer() {
        new MicroServer(TEST_SERVER_PORT);
    }

    @BeforeEach
    public void WaitForMicroServerToBeReady() {
        // make sure spark is started before making the request
        Spark.awaitInitialization();
    }

    @AfterAll
    public static void stopTheMicroServer() {
        Spark.stop();
        Spark.awaitStop();
    }

    private static HttpResponse postRequest(String endPointPath, String requestBodyJSON) throws IOException {
        HttpPost request = new HttpPost(BASE_URL + endPointPath);
        request.setEntity(new StringEntity(requestBodyJSON, ContentType.APPLICATION_JSON));
        HttpClient httpClient = HttpClientBuilder.create().build();
        return httpClient.execute(request);
    }

    @Test
    @DisplayName("base: Valid config request succeeds with 200 status")
    public void testValidConfigRequest() throws IOException {
        String requestBodyJSON = new JSONObject()
            .put("requestType", "config")
            .toString();
        HttpResponse response = postRequest("/api/config", requestBodyJSON);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("xander3: Valid distances request succeeds with 200 status")
    public void testValidDistancesRequestPlaces() throws IOException {


        JSONObject place1 = new JSONObject();
        place1.put("name", "Seattle, WA");
        place1.put("latitude", "47.6062");
        place1.put("longitude", "122.3321");

        JSONObject place2 = new JSONObject();
        place2.put("name", "Las Vegas, NV");
        place2.put("latitude", "36.1716");
        place2.put("longitude", "115.1391");

        JSONArray places = new JSONArray();
        places.put(place1);
        places.put(place2);

        double earthRadius = 3939.0;

        String requestBodyJSON = new JSONObject()
            .put("requestType", "distances")
            .put("places", places)
            .put("earthRadius", earthRadius)
            .toString();

        log.info("Test Valid Distances Request: {}",requestBodyJSON);
        HttpResponse response = postRequest("/api/distances", requestBodyJSON);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("base: An invalid request responds with 400 status")
    public void testInvalidRequest() throws IOException {
        String invalidRequestJSON = "{ }";
        HttpResponse response = postRequest("/api/config", invalidRequestJSON);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("base: An invalid endpoint responds with 404 status")
    public void testInvalidEndpoint() throws IOException {
        String invalidRequestJSON = "{ }";
        HttpResponse response = postRequest("/api/invalid", invalidRequestJSON);
        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}