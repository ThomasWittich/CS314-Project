package com.tco.requests;

import com.tco.requests.TourRequest;
import com.tco.misc.Place;
import com.tco.misc.RandomPlaces;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import com.tco.misc.DistanceCalculator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTourRequest {
    
    private TourRequest tour;

    Place[] route = new Place[5];
    Place place1 = new Place("place1", "40.61", "-105.07");
    Place place2 = new Place("place2", "40.56", "-104.97");
    Place place3 = new Place("place3", "40.56", "-105.14");
    Place place4 = new Place("place4", "40.52", "-104.98");
    Place place5 = new Place("place5", "40.52", "-105.12");

    @BeforeEach
    public void createTour() {
        route[0] = place1;
        route[1] = place2; 
        route[2] = place3;
        route[3] = place4; 
        route[4] = place5;
    }

    @AfterEach
    public void resetPlaces() {

        route[0] = place1;
        route[1] = place2; 
        route[2] = place3;
        route[3] = place4; 
        route[4] = place5;
    }

    @BeforeEach
    public void createTourForTestCases() {
        tour = new TourRequest();
        tour.setEarthRadius(3958.8);
        tour.buildResponse();
    }

    @Test
    @DisplayName("xander3: Request type is \"tour\"")
    public void testType() {
        String type = tour.getRequestType();
        assertEquals("tour", type);
    }

    @Test
    @DisplayName("witticht: calling algorithms")
    public void testAlgorithms() {
        
        route = TourRequest.algorithmOperator(route, 5.0);

        assertEquals(true, place1.compareTo(route[0]));
        assertEquals(true, place3.compareTo(route[1]));
        assertEquals(true, place5.compareTo(route[2]));
        assertEquals(true, place4.compareTo(route[3]));
        assertEquals(true, place2.compareTo(route[4]));
    }

    @Test
    @DisplayName("xander3: test 2 places") 
    public void testTwoPlaces() {
        tour = new TourRequest();
        tour.setEarthRadius(3958.8);
        Place[] places = RandomPlaces.createPlaces(2);
        tour.setPlaces(Arrays.asList(places));
        tour.buildResponse();

        assertEquals(true, places[0].compareTo(tour.getPlaces().get(0)));
        assertEquals(true, places[1].compareTo(tour.getPlaces().get(1)));
    }

    @Test
    @DisplayName("xander3: test five places")
    public void testFivePlaces() {

        tour = new TourRequest();
        tour.setEarthRadius(3958.8);
        Place[] places = RandomPlaces.createPlaces(5);
        int dist = totalDistance(places);

        //assertEquals(true, places[0].compareTo(tour.getPlaces().get(0)));
    }

    @Test
    @DisplayName("xander3: test total distance")
    public void testTotalDistance() {
        tour = new TourRequest();
        tour.setEarthRadius(3958.8);
        tour.setResponse(1.0);
        Place[] places = RandomPlaces.createPlaces(5);
        int length = places.length;
        tour.setPlaces(Arrays.asList(places));
        int distance = totalDistance(places);
        tour.buildResponse();
        List<Place> optomized = tour.getPlaces();
        //int size = optomized.size();
        int newDistance = totalDistance(optomized.toArray(new Place[optomized.size()]));
        assertEquals(true, newDistance <= distance);
    }

    public int totalDistance(Place[] places) {

        int distance = 0;

        for(int i = 0; i < places.length - 1; i++) {
            distance += DistanceCalculator.calculate(places[i], places[i+1], 3958.8);
        }

        return distance;

    }

}
