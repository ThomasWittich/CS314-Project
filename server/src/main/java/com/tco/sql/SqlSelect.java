package com.tco.sql;

import java.util.List;
import com.tco.requests.FindRequest;
import com.tco.misc.FindType;

import com.tco.sql.SqlUtility;

public class SqlSelect {

	public static String matchCountryId(String match, int limit) {
		return statementFindCountry(match, limit);
	}

	public static String matchPlacesFromCountry(String match, String countryCode, int limit) {
		return statementFindByCountry(match, countryCode, "DISTINCT " + SqlUtility.placesColumns, "LIMIT " + limit);
	}

	public static String match(String match,  int limit) {
		return statement(match, "DISTINCT " + SqlUtility.joinedPlacesColumns, "LIMIT " + limit, null);
	}

	public static String typeMatch(String match,  int limit, List<FindType> type) {


		String formattedType = "(\'";

		for (int i = 0; i < type.size(); i++){
			formattedType += type.get(i);
			if (i != type.size() -1){
				formattedType += "\' , \'";
			}
		}

		formattedType += "\')";
		return statement(match, "DISTINCT " + SqlUtility.joinedPlacesColumns, "LIMIT " + limit, formattedType);
	}

	public static String found(String match) {
		return statement(match, "COUNT(*) AS count ", "", null);
	}

	public static String statementFindCountry(String match, int limit) {
		return "select id from country where id like \""+match+"\" or name like \""+match+"\" limit "+limit+";";
	}

	public static String statementRandomPlaces(String data, int limit) {
		return "SELECT "
			+ data
			+ " FROM " + SqlUtility.placesTable
			+ " ORDER BY RAND() LIMIT "
			+ limit
			+ ";";
	}

	public static String statementFindByCountry(String match, String countryCode, String data, String limit) {
		return "SELECT "
			+ data
			+ " FROM " + SqlUtility.placesTable
			+ " WHERE iso_country LIKE \"%" + countryCode + "%\""
			+ " AND name LIKE \"%" + match + "%\""
			+ limit
			+ ";";
	}

	public static String statement(String match, String data, String limit, String type) {
		String typeFormatted = "";
		if (type != null){
			typeFormatted += "AND type in ";
			typeFormatted += type;
		}
		return "SELECT "
			+ data
			+ " FROM " + SqlUtility.placesTable
			+ " INNER JOIN country ON country.id = world.iso_country"
			+ " INNER JOIN region ON region.id = world.iso_region"
			+ " INNER JOIN continent ON continent.id = world.continent"
			+ " WHERE world.name LIKE \"%" + match + "%\" OR"
			+ " world.iata_code LIKE \"%" + match + "%\" OR"
			+ " world.gps_code LIKE \"%" + match + "%\" OR"
			+ " continent.name LIKE \"%" + match + "%\" OR"
			+ " world.continent LIKE \"%" + match + "%\" OR"
			+ " world.municipality LIKE \"%" + match + "%\" OR"
			+ " country.name LIKE \"%" + match + "%\" OR"
			+ " region.name LIKE \"%" + match + "%\" "
			+ typeFormatted
			+ limit
			+ " ;";
	}

}
