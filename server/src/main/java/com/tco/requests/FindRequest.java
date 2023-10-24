package com.tco.requests;

import com.tco.misc.BadRequestException;

import java.util.List;
import java.util.ArrayList;

import com.tco.sql.SqlUtility;
import com.tco.misc.Place;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.FindType;

public class FindRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(ConfigRequest.class);
    private ArrayList<Place> places;
    private Integer found;
    private String match;
    private Integer limit; 
    public List<FindType> type;
    public List<String> where; 

    @Override
    public void buildResponse() throws BadRequestException { 
        // places = new ArrayList<>(); 

        // places.add("reykjavik", 80.89, -141.23);

        if(match.equals("specialmatch123")) {
            findRandomPlaces();
        } else if (where != null || type != null){
            findPlacesType();
        } else {
            findPlaces();
        }

        log.trace("buildResponse -> {}", this);
        log.info("buildResponse -> {}", this);
        log.info("Find Request Build 1");
    }

    public void findRandomPlaces() {
        try {
            places = SqlUtility.randomPlaces(limit);
            found = limit;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void findPlacesType() {
        try {
            places = SqlUtility.placesbyTypeWhere(match, limit, type);
            found = SqlUtility.found(match);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void findPlaces() {
        try {
            places = SqlUtility.places(match, limit);
            found = SqlUtility.found(match);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /* The following methods exist only for testing purposes and are not used
  during normal execution, including the constructor. */

    public FindRequest(String match, int limit) {
        setRequestType();
        setMatch(match);
        setLimit(limit);
        setWhere(null);
    }


    public FindRequest(String match, int limit, List<String> where) {
        setRequestType();
        setMatch(match);
        setLimit(limit);
        setWhere(where);
    }

    public FindRequest(String match, int limit, List<String> where, List<FindType> type) {
        setRequestType();
        setMatch(match);
        setLimit(limit);
        setWhere(where);
        setType(type);
    }

    public void setRequestType(){
        this.requestType = "find";
    }

    public void setMatch(String match){
        this.match = match; 
    }

    public void setLimit(int limit){
        this.limit = limit;
    }

    public void setWhere(List<String> where){
        this.where = where; 
    }

    public void setType(List<FindType> type){
        this.type = type; 
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public String getMatch() {
        return match;
    }

    public int countPlaces() {
        return places.size();
    }

}