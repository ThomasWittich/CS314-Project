package com.tco.misc;

import static com.tco.misc.DistanceCalculator.calculate;
//import static com.tco.misc.DistanceCalculator.useRandom;
import static java.lang.Math.toRadians;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.Place;

public class TestDistanceCalculator {

    final static long small = 1L;
    final static long smallPi = Math.round(Math.PI * small);
    final static long smallPiHalf = Math.round(Math.PI / 2.0 * small);

    final static long large = 1000000L;
    final static long largePi = Math.round(Math.PI * large);
    final static long largePiHalf = Math.round(Math.PI / 2.0 * large);

    final static Place origin = new Place("Test Place", "0.0", "0.0");
    final static Place fakeOrigin = new Place("Test Place", "0.0", "0.0");

    @Test
    @DisplayName("witticht: testing for origin var")
    public void testOriginAndFake() {
        assertEquals(0L, calculate(origin, fakeOrigin, large));
    }

    @Test
    @DisplayName("witticht: distance should be 0")
    public void testDistanceToItself() {
        assertEquals(0L, calculate(origin, origin, large));
        assertEquals(0L, calculate(origin, origin, small));

    }

    final static Place reykjavik = new Place("reykvajik","64.1466","-21.9426");
    final static Place castleRock = new Place("Castle Rock","39.3722","-104.8561");

    @Test
    @DisplayName("witticht: distance between two real locations")
    public void testDistanceWithTwoReal(){
        assertEquals(456L, calculate(reykjavik,castleRock,500.0));
    }

    final static Place fake1 = new Place("fake1","-35.3214","-29.9426");
    final static Place fake2 = new Place("fake2","89.3722","119.8561");

    @Test
    @DisplayName("witticht: distance between two made up locations")
    public void testDistanceWithTwoFake(){
        assertEquals(131804L, calculate(fake1,fake2,60000.0));
    }
}
