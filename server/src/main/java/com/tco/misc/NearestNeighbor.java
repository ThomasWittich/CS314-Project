package com.tco.misc; 

public class NearestNeighbor {
 
    public static double EARTH_RADIUS = 3958.8;
    static int[] visitedFlags;

    public static Place[] nearestNeighbor(Place[] startingPlaces, Timer localTimer) {
        Place[] bestRoute = null;
        MutableLong bestRouteLength = new MutableLong(Long.MAX_VALUE);

        for (int i = 0; i < startingPlaces.length; i++) {

            bestRoute = getBestRoute(i, startingPlaces, bestRoute, bestRouteLength);
            if (localTimer.timeElapsed() >= 300L) {
                break;
            }
        }

        System.out.println("Best Route Length: " + bestRouteLength.getValue());
        return bestRoute;
    }

    public static Place[] getBestRoute(int i, Place[] startingPlaces, Place[] bestRoute, MutableLong bestRouteLength) {
        Place[] route = new Place[startingPlaces.length];
        visitedFlags = new int[startingPlaces.length];

        route[0] = startingPlaces[i];

        visitedFlags[i] = 1;
        // unvisitedPlaces[i].setVisited();

        int routePosition = 1;
        Long routeLength = 0L;

        while (routePosition < route.length) {
            MutableInt unvisitedPlacesIndex = new MutableInt(-1);
            MutableLong newPlaceDistance = new MutableLong(Long.MAX_VALUE);

            Place newPlace = getNewPlace(startingPlaces, route[routePosition - 1], newPlaceDistance,
                    unvisitedPlacesIndex);

            route[routePosition] = newPlace;
            routeLength += newPlaceDistance.getValue();
            routePosition++;
            visitedFlags[unvisitedPlacesIndex.getValue()] = 1;
            // unvisitedPlaces[unvisitedPlacesIndex.getValue()].setVisited();
        }

        bestRoute = setBestRoute(routeLength, bestRouteLength, bestRoute, route);

        return bestRoute;
    }

    public static Place[] setBestRoute(Long routeLength, MutableLong bestRouteLength, Place[] bestRoute,
            Place[] route) {
        if (routeLength < bestRouteLength.getValue()) {
            bestRoute = route;
            bestRouteLength.setValue(routeLength);
        }

        return bestRoute;
    }

    public static Place getNewPlace(Place[] unvisitedPlaces, Place lastPlace,
            MutableLong newPlaceDistance,
            MutableInt unvisitedPlacesIndex) {

        Place newPlace = null;

        for (int j = 0; j < unvisitedPlaces.length; j++) {
            if (visitedFlags[j] == 1)
                continue;

            Long distance = DistanceCalculator.calculate(lastPlace, unvisitedPlaces[j], EARTH_RADIUS);

            if (distance < newPlaceDistance.getValue()) {
                newPlace = unvisitedPlaces[j];
                unvisitedPlacesIndex.setValue(j);
                newPlaceDistance.setValue(distance);
            }
        }

        return newPlace;
    }

}
