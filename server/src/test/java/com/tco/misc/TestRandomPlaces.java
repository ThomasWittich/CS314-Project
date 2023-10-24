package com.tco.misc;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestRandomPlaces {

    @BeforeEach
    public void RandomPlacesTest() {
        RandomPlaces.rand.setSeed(3);
    }

    @Test
    @DisplayName("ju1es16: test create places none")
    public void testCreatePlacesNone() {
        Place[] placeNothing = RandomPlaces.createPlaces(0);
        assertEquals(placeNothing.length, 0);
    }

    @Test
    @DisplayName("ju1es16: test create places")
    public void testCreatePlaces() {
        Place[] place = RandomPlaces.createPlaces(5);
        assertEquals(place.length, 5);
    }

    @Test
    @DisplayName("ju1es16: test lat")
    public void testLat() {
        Double lat = RandomPlaces.getLatitude();
        assertEquals(lat, -82.4);
    }

    @Test
    @DisplayName("ju1es16: test lon")
    public void testLon() {
        Double lon = RandomPlaces.getLongitude();
        assertEquals(lon, 148.6);
    }
}
