package com.tco.requests;

import com.tco.misc.TourCalculator;
import com.tco.misc.NearestNeighbor;
import com.tco.misc.Timer;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import com.tco.misc.Place;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TourRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(TourRequest.class);
    private List<Place> places;
    private Double response;
    private Double earthRadius;

    @Override
    public void buildResponse() { 
        TourCalculator.EARTH_RADIUS = earthRadius;
        NearestNeighbor.EARTH_RADIUS= earthRadius;
        if(places != null && places.size() > 3) {
            List<Place> newPlaces = Arrays.asList(algorithmOperator(places.toArray(new Place[places.size()]), response));
            if (TourCalculator.calculateTotalDistance(places) > TourCalculator.calculateTotalDistance(newPlaces)){
                places = newPlaces;
            }
        }
        log.trace("buildResponse -> {}", this);
    }

    public static Place[] algorithmOperator(Place[] startingPlaces, double timeAllowed) {
        Timer timer = new Timer(timeAllowed);
        Place[] optimalRoute = startingPlaces;

        if (startingPlaces.length <= 1750){
            optimalRoute = NearestNeighbor.nearestNeighbor(startingPlaces, timer);
        }

        if (timer.timeLeft() && startingPlaces.length <= 825) {
            TourCalculator.twoOpt(optimalRoute, timer);
        }

        if (timer.timeLeft() && startingPlaces.length <= 95) {
            TourCalculator.threeOpt(optimalRoute, timer);
        }

        resetStart(optimalRoute, timer, startingPlaces[0]);
        
        return optimalRoute;
    }

    public static void resetStart(Place[] route, Timer timer, Place start){
        if(route[0] != start){
            TourCalculator.cycleToStart(route, timer, start);
        }
    }
    /* Below methods are used for testing */
    public TourRequest() {
        this.requestType = "tour";
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setEarthRadius(Double radius) {
        earthRadius = radius;
    }

    public void setResponse(Double response) {
        this.response = response;
    }

}
