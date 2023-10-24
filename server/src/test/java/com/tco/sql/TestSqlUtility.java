package com.tco.sql;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.Place;
import com.tco.misc.FindType;

public class TestSqlUtility {

    private static final Logger log = LoggerFactory.getLogger(SqlUtility.class);

    @Test
    @DisplayName("xander3: sql query test")
    public void testValidSqlQuery() {
        log.info("Test Sql Query");
        Integer found = 0;
        try {
			String match = "dave";
			Integer limit = 100;
			found = SqlUtility.found(match);
            log.info("\n");
			//System.out.printf("%d found, %d returned\n", found, places.size());
		} catch (Exception e) {
            log.info("Exception: "+e.getMessage());
            //System.err.println("Exception: " + e.getMessage());
		}

        assertEquals(28, found);
    }

    @Test
    @DisplayName("xander3: sql query country test")
    public void testValidCountryTest() {
        log.info("Test Sql Query Country");
        String id = "";
        try {
			String match = "us";
			id = SqlUtility.country(match, (Integer) 1);
            log.info("Test Sql Query Country Code: "+id);
            log.info("\n");
		} catch (Exception e) {
            log.info("Exception: "+e.getMessage());
		}

        assertEquals("US", id);
    }

    @Test
    @DisplayName("xander3: sql query country test")
    public void testValidPlacesByCountry() {
        log.info("Test Sql Query Country [places by country]");
        ArrayList<Place> found = new ArrayList<>();
        try {
			String match = "us";
			String id = SqlUtility.country(match, (Integer) 1);
            log.info("Test Sql Query Country Code [places by country]: "+id);
            match = "a";
			Integer limit = 5;
			found = SqlUtility.placesByCountry(match, id, limit);
            log.info("\n");
		} catch (Exception e) {
            log.info("Exception: "+e.getMessage());
		}

        assertEquals(5, found.size());
    }

    @Test
    @DisplayName("witticht: checking the count function")
    public void testCount(){
        Integer numFound = 0;
        try {
            Integer limit = 5;
            String match = "apple";
            ArrayList<Place> places = SqlUtility.places(match, limit);
            numFound = places.size();
        } catch (Exception e) {
            log.info("Exception: "+e.getMessage());
        }
        assertEquals(5, numFound);
    }

    @Test
    @DisplayName("witticht: testing the place function")
    public void testPlace(){
        Integer numFound = 0;
        try {
            Integer limit = 3;
            String match = "banana";
            ArrayList<Place> places = SqlUtility.places(match, limit);
            numFound = places.size();
        } catch (Exception e) {
            log.info("Exception: "+e.getMessage());
        }
        assertEquals(3, numFound);
    }

    @Test
    @DisplayName("witticht: testing an empty request")
    public void testEmpty(){
        Integer numFound = 0;
        try {
            String match = "";
            numFound = SqlUtility.found(match);
        } catch (Exception e) {
            log.info("Exception: "+e.getMessage());
        }
        assertEquals(50427,numFound);
    }

    @Test
    @DisplayName("witticht: testing a response with bad match")
    public void testIncorrectMatch(){
        Integer numFound = 0;
        try {
            String match = "THIS IS AN INVALID REQUEST";
            numFound = SqlUtility.found(match);
        } catch (Exception e) {
            log.info("Exception: "+e.getMessage());
        }
        assertEquals(0,numFound);
    }

    @Test
    @DisplayName("witticht: testing airport code")
    public void testAirportCode(){
        Integer numFound = 0;
        try {
            String match = "FNL";
            numFound = SqlUtility.found(match);
        } catch (Exception e){
            log.info("Exception" + e.getMessage());
        }
        assertEquals(numFound, 7);
    }

    @Test
    @DisplayName("xander3: testing randomplaces")
    public void testRandomPlaces(){

        try {
            SqlUtility.randomPlaces(5);
        } catch (Exception e){
            log.info("Exception" + e.getMessage());
        }

    }
}
