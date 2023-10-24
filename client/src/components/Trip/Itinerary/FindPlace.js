import React from 'react';
import { getOriginalServerUrl, sendAPIRequest } from '../../../utils/restfulAPI';
import serverSettings from '../../../utils/serverSettings';

export async function getPlaces(destinationString) {
    let response = null;
    let foundPlaces = undefined;
    if(serverSettings.activeServerUrl != undefined){
        response = await getFindResponse(destinationString);
    }
    if (response != null){
        foundPlaces = response.places;
    }
    return foundPlaces;
    //does server request
	//if fail, set found place to undefined
}

export async function getRandomPlaces() {
    const response = await sendAPIRequest(getRandomRequest(), serverSettings.activeServerUrl);
    console.log(response);
    return response.places;
}

export async function getFindResponse(destinationString, testResponse) {
    let request = testResponse || getFindRequest(destinationString);
    const response = await sendAPIRequest(request, serverSettings.activeServerUrl);
    return response;
}

export function getRandomRequest() {
    let request = {
        requestType: "find",
        match: "specialmatch123",
        limit: 5
    }
    return request;
}

export function getFindRequest(destinationString) {
    let request = {
        requestType: "find",
        match: destinationString,
        limit: 5
    }
    return request;
}