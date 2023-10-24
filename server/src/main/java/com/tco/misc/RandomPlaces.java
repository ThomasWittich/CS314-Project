package com.tco.misc;

import java.util.Random;

public class RandomPlaces {

    public static Random rand = new Random();

    public static Place[] createPlaces(int numberOfPlaces) {
        Place[] places = new Place[numberOfPlaces];

        for (int i = 0; i < numberOfPlaces; i++) {
            places[i] = newPlace(i);
        }

        return places;
    }

    public static Place newPlace(int placeNumber) {
        String placeName = "Place " + placeNumber;
        double latitude = getLatitude();
        double longitude = getLongitude();
        return new Place(placeName, Double.toString(latitude), Double.toString(longitude));
    }

    // -90 to 90
    public static double getLatitude() {
        return (double) randInt(-90, 90) + randInt(0, 99) / 100.0;
    }

    // -180 to 180
    public static double getLongitude() {
        return (double) randInt(-180, 180) + randInt(0, 99) / 100.0;
    }

    public static int randInt(int min, int max) {
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
