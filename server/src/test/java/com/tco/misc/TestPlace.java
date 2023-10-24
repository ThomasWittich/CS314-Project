package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPlace {
    
    Place place;

    @BeforeEach
    public void createConfigurationForTestCases() {
        place = new Place("Place Name", "1.0", "2.0");
    }

    @Test
    @DisplayName("xander3: Check place name")
    public void testPlaceName() {
        assertEquals("Place Name", place.getName());
    }

    @Test
    @DisplayName("xander3: Check latitude")
    public void testLatitude() {
        assertEquals(1.0, place.getLatitude());
    }

    @Test
    @DisplayName("xander3: Check longitude")
    public void testLongitude() {
        assertEquals(2.0, place.getLongitude());
    }

    @Test
    @DisplayName("witticht: check to string method")
    public void testToString() {
        Place testPlace = new Place("testplace", "1", "1");
        assertEquals(testPlace.toString(), "testplace 1 1");
    }

    @Test
    @DisplayName("xander3: check new places")
    public void testFullPlace() {
        Place newPlace = new Place("name","1","2", "id", "altitude","municipality","type","region","country","url");
        assertEquals("name", newPlace.getName());
        assertEquals(1.0, newPlace.getLatitude());
        assertEquals(2.0, newPlace.getLongitude());
        assertEquals("id", newPlace.id);
        assertEquals("altitude", newPlace.altitude);
        assertEquals("municipality", newPlace.municipality);
        assertEquals("type", newPlace.type);
        assertEquals("region", newPlace.region);
        assertEquals("country", newPlace.country);
        assertEquals("url", newPlace.url);
    }

}
