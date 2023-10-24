import React, { useEffect, useState } from 'react';
import { beforeEach, describe, expect, jest, test } from '@jest/globals';
import user from '@testing-library/user-event';
import { render, screen, waitFor } from '@testing-library/react';
import { VALID_DISTANCES_REQUEST, VALID_DISTANCES_RESPONSE } from '../../sharedMocks';
import { getOriginalServerUrl, sendAPIRequest } from '../../../src/utils/restfulAPI';
import {countDistance, getDistancesRequest, temporaryPlaceJSONCreator} from '../../../src/components/Trip/Map/TotalDistance';
import {
	REVERSE_GEOCODE_RESPONSE,
	MOCK_PLACE_RESPONSE,
} from '../../sharedMocks';

describe('TotalDistance', () => {
    
    // Initializes locations 
	
	test('Hailey: test temporaryPlaceJSONCreator format', async () => {
        expect(temporaryPlaceJSONCreator ("Denver", 39.73715, -104.989174)).toEqual('{"name":"Denver","latitude":39.73715,"longitude":-104.989174}');
	});

	test('xander3: test count distances', async () => {

		let distances = new Array(2, 2);

        expect(countDistance(distances)).toEqual(4);
	});

	test('xander3: test get distances request', async () => {

		let places = [{"name":"Miami, Florida", "latitude":  "25.8",  "longitude": "80.2"},
		{"name": "Bermuda", "latitude":  "32.3", "longitude": "64.7"},
		{"name": "San Juan, Puerto Rico", "latitude":  "18.5", "longitude": "66.1"},
		{"name": "Turks and Caicos", "latitude":  "21.7", "longitude": "71.8"}];

        expect(JSON.stringify(getDistancesRequest(places, 3959))).toEqual(VALID_DISTANCES_REQUEST);
	});

	/*test('xander3: get distances response', async () => {
		fetch.mockResponse(VALID_DISTANCES_REQUEST);

		const response = await sendAPIRequest(JSON.parse(VALID_DISTANCES_REQUEST), 'http://localhost:31400');
		expect(response).toEqual(JSON.parse(VALID_DISTANCES_REQUEST));

	});*/

});
