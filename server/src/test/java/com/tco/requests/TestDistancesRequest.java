package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.ServerSocket;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import com.tco.misc.Place;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

public class TestDistancesRequest {

    private DistancesRequest dist;
    private DistancesRequest dist2;
    private DistancesRequest dist3;
    private DistancesRequest dist4;
    private static final transient Logger log = LoggerFactory.getLogger(TestDistancesRequest.class);

    @BeforeEach
    public void createDistancesForTestCases() {
        Place castleRock = new Place("Castle Rock","39.3722","-104.8561");

        List<Place> places = new ArrayList<>();
        List<Place> places2 = new ArrayList<>();
        
        dist3 = new DistancesRequest(places, 500.0);
        dist3.buildResponse();

        places.add(new Place("reykvajik","64.1466","-21.9426"));
        places.add(castleRock);
        places2.add(castleRock);
        places2.add(castleRock);
        
        dist = new DistancesRequest(places, 500.0);
        dist2 = new DistancesRequest(places, Double.MAX_VALUE);
        dist4 = new DistancesRequest(places2, 500.0);
        
        dist.buildResponse();
        dist2.buildResponse();
        
        dist4.buildResponse();
        
    }

    @Test
    @DisplayName("xander3: Request type is \"distances\"")
    public void testType() {
        String type = dist.getRequestType();
        assertEquals("distances", type);
    }

    @Test
    @DisplayName("xander3: correct earth radius")
    public void testEarthRadius() {
        assertEquals(500.0, dist.getEarthRadius());
    }


    @Test
    @DisplayName("xander3: correct number of places")
    public void testTotalPlaces() {
        assertEquals(2, dist.getPlaces().size());
    }

    @Test
    @DisplayName("xander3: distance between two locations")
    public void testDistances(){
        assertEquals(456L, dist.getDistances().get(0));
        assertEquals(456L, dist.getDistances().get(1));
    }

    @Test
    @DisplayName("hae123: correct earth radius with max radius")
    public void testMaxRadius(){
        assertEquals(Double.MAX_VALUE, dist2.getEarthRadius());
    }


    @Test
    @DisplayName("hae123: correct distances with max radius")
    public void testMaxRadiusDistances(){
        
        long test1 = (long) ((dist.getDistances().get(1)/500.0)*Double.MAX_VALUE);
        //log.info("TEST!:" + ((long) test1) + " " + Double.MAX_VALUE);
        assertEquals(test1, dist2.getDistances().get(0));
        assertEquals (test1 , dist2.getDistances().get(1));
    }   

    @Test
    @DisplayName("hae123: repeat places")
    public void testRepeatPlacesDistances(){

        assertEquals(0L, dist4.getDistances().get(0));
        assertEquals (0L , dist4.getDistances().get(1));
    }  

    @Test
    @DisplayName("hae123: no places")
    public void testNoPlacessDistances(){
        //log.info("TEST1: " + dist3.getDistances.size());
        //long test1 = (long) ((dist.getDistances().get(1)/500.0)*Double.MAX_VALUE);
        //log.info("TEST!:" + ((long) test1) + " " + Double.MAX_VALUE);
        assertEquals(0, dist3.getDistances().size());
        //assertEquals (test1 , dist2.getDistances().get(1));
    }  

}