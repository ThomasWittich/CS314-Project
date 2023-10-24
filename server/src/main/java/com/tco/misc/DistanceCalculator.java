package com.tco.misc;

import com.tco.misc.Place;
import java.lang.Math;


public final class DistanceCalculator {
    private DistanceCalculator(){}

    public static long calculate(
        Place FROM, 
        Place TO, 
        double earthRadius) {

            double lat1 = FROM.getLatitude();
            double lat2 = TO.getLatitude();
            double long1 = FROM.getLongitude();
            double long2 = TO.getLongitude();

            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);
            long1 = Math.toRadians(long1);
            long2 = Math.toRadians(long2);


            double deltaVal = delta(long1, long2);

            double angleRadians = Math.atan2(
		        (Math.sqrt((Math.pow(Math.cos(lat2)*Math.sin(deltaVal), 2)) + 
				(Math.pow(Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaVal), 2)))),
				(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(deltaVal)));
		    long arc = Math.round(earthRadius * angleRadians);
        
            return arc;
    }

    public static double delta(double y1, double y2) {
		return y1 - y2;
	}
}
