package com.tco.requests;

import java.util.List;
import java.util.ArrayList;

import com.tco.misc.Place;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.DistanceCalculator;

public class DistancesRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(ConfigRequest.class);
    private List<Place> places;
    private List<Long> distances; 
    private Double earthRadius;

    @Override
    public void buildResponse() {
        distances = new ArrayList<>(); 
        
        log.info("Distance Request Build 1");

        if(places != null) {
            log.info("PLACES SIZE: {}",places.size());

            addDistancesToDistancesVariable();

            log.info("DISTANCES SIZE: {}",distances.size());
        }
        
        log.trace("buildResponse -> {}", this);
        log.info("buildResponse -> {}", this);
        log.info("Distance Request Build 2");
    }

    public void addDistancesToDistancesVariable(){
        for(int i = 0; i < places.size(); i++) {
            distances.add(DistanceCalculator.calculate(places.get(i), places.get(changeEndPointIfAtEnd(i)), earthRadius));
        }
    }

    public int changeEndPointIfAtEnd(int i){
        if(i == places.size() - 1) {
            return 0;
        }
        return i + 1;
    }

  /* The following methods exist only for testing purposes and are not used
  during normal execution, including the constructor. */

    public DistancesRequest(List<Place> places, double earthRadius) {
        setRequestType();
        setPlaces(places);
        setEarthRadius(earthRadius);
        setDistances();
    }

    public void setRequestType(){
        this.requestType = "distances";
    }

    public void setPlaces(List<Place> places){
        this.places = places; 
    }

    public void setEarthRadius(double earthRadius){
        this.earthRadius = earthRadius;
    }

    public void setDistances(){
        this.distances = distances; 
    }

    public List<Place> getPlaces() {
        return places;
    }

    public double getEarthRadius() {
        return earthRadius;
    }

    public List<Long> getDistances() {
        return distances; 
    }

}
