package com.tco.misc;

import com.tco.misc.Timer;
import com.tco.misc.TourCalculator;
import com.tco.misc.TourCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTourCalculator {
    
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

    @Test
    @DisplayName("witticht: test calculate total tour")
    public void testCalculateTotalTour() {
        long totalDist = TourCalculator.calculateTotalDistance(Arrays.asList(route));
    }

    @Test
    @DisplayName("xander3: test two opt")
    public void testTwoOpt() {

        Timer timer = new Timer(5);

        TourCalculator.twoOpt(route, timer);

        assertEquals(true, place1.compareTo(route[0]));
        assertEquals(true, place4.compareTo(route[1]));
        assertEquals(true, place2.compareTo(route[2]));
        assertEquals(true, place3.compareTo(route[3]));
        assertEquals(true, place5.compareTo(route[4]));
       
    }

    // @Test
    // @DisplayName("xander3: test three opt")
    // public void testThreeOpt() {

    //     Timer timer = new Timer(20);

    //     TourCalculator.threeOpt(route, timer);

    //     assertEquals(true, place1.compareTo(route[0]));
    //     assertEquals(true, place4.compareTo(route[1]));
    //     assertEquals(true, place2.compareTo(route[2]));
    //     assertEquals(true, place3.compareTo(route[3]));
    //     assertEquals(true, place5.compareTo(route[4]));
       
    // }

    @Test
    @DisplayName("hae123: test cycle to start")
    public void testCycleToStart() {

        Timer timer = new Timer(20);

        TourCalculator.cycleToStart(route, timer, route[2]);
        assertEquals(true, place1.compareTo(route[3]));
        assertEquals(true, place2.compareTo(route[4]));
        assertEquals(true, place3.compareTo(route[0]));
        assertEquals(true, place4.compareTo(route[1]));
        assertEquals(true, place5.compareTo(route[2]));
       
    }

}
