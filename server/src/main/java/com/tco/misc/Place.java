package com.tco.misc;

public class Place {

    private String name; 
    private String latitude; 
    private String longitude; 
    public String id; 
    public String altitude; 
    public String municipality;
    public String type; 
    public String region; 
    public String country;
    public String url; 

    public Place(String name, String latitude, String longitude) {
        this.name = name; 
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Place(String name, String latitude, String longitude, String id, String altitude, String municipality, String type, String region, String country, String url) {
        this.name = name; 
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.altitude = altitude;
        this.municipality = municipality;
        this.type = type;
        this.region = region;
        this.country = country;
        this.url = url;
    }

    public String getName() {
        return name; 
    }

    @Override
    public String toString(){
        String placeString = name + " " + latitude + " " + longitude;
        return placeString;
    }

    public double getLatitude() {
        return Double.parseDouble(latitude);
    }

    public double getLongitude() {
        return Double.parseDouble(longitude);
    }

    // Below methods are used for testing

    public boolean compareTo(Place place) {

        if(!this.name.equals(place.getName()) || 
            place.getLatitude() != getLatitude() ||
            place.getLongitude() != getLongitude())
            return false;

        return true; 
    }

}
