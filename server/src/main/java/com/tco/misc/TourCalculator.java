package com.tco.misc;

import com.tco.misc.Place;
import com.tco.misc.DistanceCalculator;
import java.util.List;

public class TourCalculator {

    public static double EARTH_RADIUS = 3958.8;

    public static int routeSwapI = 0;
    public static int routeSwapK = 0;

    public static long calculateTotalDistance(List<Place> route){
        long totalDist = 0;
        for (int i = 0; i < route.size() - 1; i++){
          totalDist += DistanceCalculator.calculate(route.get(i), route.get(i + 1), EARTH_RADIUS);
        }
        return totalDist;
    }

    public static void threeOpt(Place[] route, Timer timer) {
        boolean improvement = true;
        int n = route.length - 1;

        while (improvement) {
            improvement = threeOptIterate(n, route);
            if (timer.timeElapsed() >= 750L) {
                break;
            }
        }
    }

    public static boolean threeOptIterate(int n, Place[] route) {
        boolean improvement = false; 

        for (int i = 0; i <= n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                improvement = threeOptImprove(i, j, n, route);
            }
        }

        return improvement;
    }

    public static boolean threeOptImprove(int i, int j, int n, Place[] route) {

        boolean improvement = false;

        for (int k = j + 1; k <= n - 1; k++) {
            byte reversals = threeOptReversals(route, i, j, k);
            int[] indexes = new int[3];
            indexes[0] = i; 
            indexes[1] = j; 
            indexes[2] = k;
            reversals(reversals, indexes, route);
            if (reversals > 0) {
                improvement = true;
            }
        }

        return improvement;
    }



    public static void reversals(byte reversals, int[] indexes, Place[] route) {
        int i = indexes[0];
        int j = indexes[1];
        int k = indexes[2];

        if (threeOptReversalI1J(reversals)) 
            twoOptReverse(route, i + 1, j);
        if (threeOptReversalJ1K(reversals)) 
            twoOptReverse(route, j + 1, k);
        if (threeOptReversalI1K(reversals)) 
            twoOptReverse(route, i + 1, k);
    }

    public static boolean threeOptReversalI1J(byte reversals) {
        return (reversals & 0b001) > 0;
    }

    public static boolean threeOptReversalJ1K(byte reversals) {
        return (reversals & 0b010) > 0;
    }

    public static boolean threeOptReversalI1K(byte reversals) {
        return (reversals & 0b100) > 0;
    }

    public static byte threeOptReversals(Place[] route, int i, int j, int k) {

        byte mask = 0b000;

        if(twoOptImproves(route, i, j)) {
            mask = 0b001;
        } else if(twoOptImproves(route, j, k)) {
            mask = 0b010;
        } else if(twoOptImproves(route, i, k)) {
            mask = 0b100;
        }

        return mask;
    }

    public static void twoOpt(Place[] route, Timer timer) {
        boolean improvement = true;
        int n = route.length - 1;

        while (improvement) {
            improvement = false;
            improve(route, n);

            if (timer.timeElapsed() >= 700L) {
                break;
            }
        }

    }

    public static boolean improve(Place[] route, int n) {
        boolean improvement = false; 

        for (int i = 0; i <= n - 3; i++) {
            improvement= performImprovement(i, n, route);
        }

        return improvement;
    }

    public static boolean performImprovement(int i, int n, Place[] route) {
        boolean improvement = false;

        for (int k = i + 2; k <= n - 1; k++) {
            if (twoOptImproves(route, i, k)) {
                twoOptReverse(route, i + 1, k);
                improvement = true;
            }
        }

        return improvement;
    }

    public static boolean twoOptImproves(Place[] route, int i, int k) {
        return legDistance(route, i, k) + legDistance(route, i+1, k+1) 
            < legDistance(route, i, i + 1) + legDistance(route, k, k+1);
    }

    public static Long legDistance(Place[] route, int indexOne, int indexTwo) {
        double earthRadius = EARTH_RADIUS;
        return DistanceCalculator.calculate(route[indexOne], route[indexTwo], earthRadius);
    }

    public static void routeSwap(Place[] route){
        Place temp = route[routeSwapI];
        route[routeSwapI] = route[routeSwapK];
        route[routeSwapK] = temp; 
        routeSwapI++;
        routeSwapK--;
    }

    public static void twoOptReverse(Place[] route, int i, int k) {
        routeSwapI = i;
        routeSwapK = k;
        System.out.println("reverse");
        while(routeSwapI < routeSwapK) {
            System.out.println("reverse 2");
            routeSwap(route);
        }

        if(routeSwapI == routeSwapK) {
            System.out.println("reverse 3");
            routeSwap(route);
        }
    }

    public static void cycleToStart(Place[] route, Timer timer, Place start) {
        int index = 0;
        int len = route.length;
        Place[] temp = new Place[route.length];
        for(index = 0; index < len; index++){
            if (route[index] == start){ break;}
        }
        copyArray(temp, route);
        copyArrayFromIndex(temp, route, index);
    }
    private static void copyArray(Place[] temp, Place[] route){
        for(int i = 0; i < route.length; i++){
            temp[i] = route[i];
        }
    }
    
    private static void copyArrayFromIndex(Place[] temp, Place[] route, int index){
        for(int i = 0; i < route.length; i++){
            route[i] = temp[index++];
            if(index == route.length){index = 0;}
        }
    }
}
