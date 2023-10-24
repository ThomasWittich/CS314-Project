package com.tco.sql;

/* Must add a library for Mariadb before execution. */
import com.tco.sql.SqlSelect;
import com.tco.misc.Place;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.lang.Exception;

import com.tco.requests.FindRequest;
import com.tco.misc.FindType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlUtility {

    private static final Logger log = LoggerFactory.getLogger(SqlUtility.class);
	public final static String placesTable = "world";
	public final static String placesColumns = "id,type,name,municipality,iso_region,iso_country,continent,latitude,longitude,altitude,home_link";
	public final static String joinedPlacesColumns = "world.id,world.type,world.name,world.municipality,world.iso_region,world.iso_country,world.continent,world.latitude,world.longitude,world.altitude,world.home_link";
    private SqlUtility() {
       //Private constructor to stop new sqlutility objects from being created
    }

	public static Integer found(String match) throws Exception {
		String sql = SqlSelect.found(match);
		log.info("Found SQL: "+sql);
		ResultSet results = queryResults(sql);
		return count(results);
	}

	public static ArrayList<Place> randomPlaces(int limit) throws Exception {
		String sql = SqlSelect.statementRandomPlaces(placesColumns, limit);
		log.info("Random Places SQL: "+sql);
		ResultSet results = queryResults(sql);
		return convertQueryResultsToPlaces(results);
	}

	public static ArrayList<Place> places(String match, Integer limit) throws Exception {
		String sql = SqlSelect.match(match, limit);
		log.info("Places SQL: "+sql);
		ResultSet results = queryResults(sql);
		return convertQueryResultsToPlaces(results);
	}

	public static ArrayList<Place> placesbyTypeWhere(String match, Integer limit, List<FindType> type) throws Exception {
		String sql = SqlSelect.typeMatch(match, limit, type);
		log.info("Places SQL by type or where: "+sql);
		ResultSet results = queryResults(sql);
		return convertQueryResultsToPlaces(results);
	}

	public static String country(String match, Integer limit) throws Exception {
		String sql = SqlSelect.matchCountryId(match, limit);
		log.info("Country SQL: "+sql);
		ResultSet results = queryResults(sql);
		if(results.next()) {
			return results.getString("id");
		} else {
			return "";
		}
	}

	public static ArrayList<Place> placesByCountry(String match, String countryId, Integer limit) throws Exception {
		String sql = SqlSelect.matchPlacesFromCountry(match, countryId, limit);
		log.info("Places By Country SQL: "+sql);
		ResultSet results = queryResults(sql);
		return convertQueryResultsToPlaces(results);
	}

	public static ResultSet queryResults(String sql) throws Exception {
		String url      = Credential.url();
		String user     = Credential.USER;
		String password = Credential.PASSWORD;
		try (
			// connect to the database and query
			Connection conn    = DriverManager.getConnection(url, user, password);
			Statement  query   = conn.createStatement();
		) {
			return query.executeQuery(sql);
		} catch (Exception e) {
			throw e;
		}
	}

	public static ArrayList<Place> convertQueryResultsToPlaces(ResultSet results) throws Exception {
		ArrayList<Place> places = new ArrayList<>();
		while (results.next()) {

			Place place = new Place(results.getString("name"), results.getString("latitude"), results.getString("longitude"), results.getString("id"), results.getString("altitude"), results.getString("municipality"), results.getString("type"), results.getString("iso_region"), results.getString("iso_country"), results.getString("home_link"));

			places.add(place);
		}
		log.info("Places Size Converty Query Results: "+places.size());
		return places;
	}

	public static Integer count(ResultSet results) throws Exception {
		if (results.next()) {
			return results.getInt("count");
		}
		throw new Exception("No count results in found query.");
	}

	public static class Credential {
		// shared user with read-only access
		final static String USER = "cs314-db";
		final static String PASSWORD = "eiK5liet1uej";
		
		public static String url() {
            // Note that if the variable isn't defined, System.getenv will return null
			String useTunnel = System.getenv("CS314_USE_DATABASE_TUNNEL");
            // connection information when using port forwarding from localhost
            if(useTunnel != null && useTunnel.equals("true")) {
                return "jdbc:mariadb://127.0.0.1:56247/cs314";
            }
            else {
                return "jdbc:mariadb://faure.cs.colostate.edu/cs314";
            }

		}
	}

}