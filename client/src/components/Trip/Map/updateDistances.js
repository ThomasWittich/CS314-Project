import { getDistanceResponse, countDistance } from './TotalDistance';
let unit = "miles";
let radius = 3959.0;


export async function updateDistances(places) {
    const distancesHeader = document.getElementById("distances-header");
    updateDistancesHeader(distancesHeader, places);
}

export async function updateDistancesHeader(distancesHeader, places){
    if(distancesHeader != null) {
        handleDistancesIfOneCanBeFound(distancesHeader, places);
    }
}

export async function handleDistancesIfOneCanBeFound(distancesHeader, places){
    let result = "Total Distance: ";
    let totalDistance = 0
    if(places.length > 1) {
        const response = await getDistanceResponse(places, radius);
        totalDistance = countDistance(response.distances);
        generateAllDistancesText(response, places);
    }
    distancesHeader.innerText = result+totalDistance + " " + unit;
}

export async function generateAllDistancesText(response, places){
    let runningTotal = 0; 
    for(let i = 0; i < response.distances.length; i++) {
        generateDistancesTextLine(response, runningTotal, i, places)
        runningTotal += response.distances[i];
    } 
}

export async function generateDistancesTextLine(response, runningTotal, i, places) {
    const cumDist = document.getElementById("place-" + i);
    const legDist = document.getElementById("legPlace-" + i);
                
    if(legDist != null){
        legDist.innerText = createLegDistanceString(response, changeDistanceStartValueIfAtEnd(i, places), i, places);
    }
    cumDist.innerText = runningTotal;
}

export function changeDistanceStartValueIfAtEnd(i, places){
    if( i == places.length - 1){
        return 0;
    }
    else{
        return i + 1;
    }
}

export function createLegDistanceString(response, secondIndex, i, places){
    return "Distance from " + places[i].defaultDisplayName + " to " + places[secondIndex].defaultDisplayName + " is " + response.distances[i] + " " + unit + ".";
}

export function updateDistanceUnits(newUnit, places){
    unit = newUnit;
    switch(unit){
        case "kilometers":
            radius = 3671.4;
            break;
        case "meters":
            radius = 3671393.0;
            break;
        case "nautical miles":
            radius = 3440.3;
            break;
        case "miles":
            radius = 3959.0;
            break;
        default:
            radius = 3959.0;
            break;
    }
    updateDistances(places);
}
