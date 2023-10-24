import React from 'react';
import { getOriginalServerUrl, sendAPIRequest } from '../../../utils/restfulAPI';
import serverSettings from '../../../utils/serverSettings';
export async function getDistanceResponse(places, radius) {
    
    let request = getDistancesRequest(places, radius);

    const response = await sendAPIRequest(request, serverSettings.activeServerUrl);

    return response;
}

export function countDistance(distances) {
    let totalDistance = 0;
    distances.forEach(distance => totalDistance += distance);
    return totalDistance;
}

export function getDistancesRequest(places, radius) {
    let request = {
        requestType: "distances",
        earthRadius: radius,
        places: []      
    }

    return putAllOfThePlacesIntoJSON(places, request);
}

export function putAllOfThePlacesIntoJSON(places, request){
    for(let i = 0; i < places.length; i++) {
        request["places"].push({latitude: places[i].latitude, longitude: places[i].longitude});
    }
    return request; 
}

export function temporaryPlaceJSONCreator (name, latitude, longitude) {
    return  JSON.stringify({"name" : name, "latitude": latitude, "longitude": longitude}) ;
}