package com.tco.requests;

import com.tco.misc.BadRequestException;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import com.tco.misc.Place;
import com.tco.sql.SqlUtility;

import com.tco.misc.FindType;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFindRequest {

    private FindRequest find;

    @BeforeEach
    public void createConfigurationForTestCases() {
        find = new FindRequest("apple",5);
        try {
            find.buildResponse();
        }
        catch(BadRequestException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("witticht: test request type")
    public void testType() {
        String type = find.getRequestType();
        assertEquals("find", type);
    }

    @Test
    @DisplayName("witticht: test name to be the same")
    public void testName() {
        String name = find.getMatch();
        assertEquals("apple", name);
    }

    @Test
    @DisplayName("witticht: test get places and count places")
    public void testGetPlaces() {
        ArrayList<Place> places = find.getPlaces();
        assertEquals(places.size(), find.countPlaces());
    }

    @Test
    @DisplayName("xander3: test findPlaces from find request")
    public void testFindPlaces() {
        FindRequest testFind = new FindRequest("apple",5);
        testFind.findPlaces();
        assertEquals(5, testFind.countPlaces());
    }

    @Test
    @DisplayName("witticht: testing type")
    public void testTypeFindPlaces() {
        FindRequest testFind = new FindRequest("Total Rf Heliport",5, null , 
            new ArrayList<FindType>(Arrays.asList(FindType.heliport)));
        testFind.findPlacesType();
        assertEquals(1, testFind.countPlaces());
    }

    @Test
    @DisplayName("milkjug: test findPlaces to make sure type and where both throw an error")
    public void testFindPlacesWhereAndType(){
        List<String> where = new ArrayList<>();
        where.add("hello");
        List<FindType> type = new ArrayList<>();
        FindType airport = FindType.airport;
        type.add(airport);
        try {
            FindRequest tester = new FindRequest("Salem", 2, where);
            tester.setType(type);
            tester.buildResponse();
        }
        catch(BadRequestException e){
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("milkjug: test findPlaces to make sure type throws an error")
    public void testFindPlacesType(){
        List<FindType> type = new ArrayList<>();
        FindType other = FindType.other;
        type.add(other);
        try {
            FindRequest tester = new FindRequest("Salem", 2);
            tester.setType(type);
            tester.buildResponse();
        }
        catch(BadRequestException e){
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("milkjug: test findPlaces to make sure where throws an error")
    public void testFindPlacesWhere(){
        List<String> where = new ArrayList<>();
        where.add("hello");
        try {
            FindRequest tester = new FindRequest("Salem", 2, where);
            tester.buildResponse();
        }
        catch(BadRequestException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("xander3: test find random places")
    public void testFindRandomPlaces(){
        try {
            FindRequest tester = new FindRequest("specialmatch123", 5);
            tester.buildResponse();
            assertEquals(5, tester.countPlaces());
        }
        catch(BadRequestException e){
            e.printStackTrace();
        }
    }
}