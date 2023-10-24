import React from 'react';
import { beforeEach, describe, expect, jest, test } from '@jest/globals';
import user from '@testing-library/user-event';
import { render, screen, waitFor } from '@testing-library/react';
import AddPlace from '../../../src/components/Header/AddPlace';
import { mustBeAddress, newFoundPlacesJson, getNewFoundPlaces, isLatLngValid } from '../../../src/components/Header/AddPlace';
import { act } from 'react-dom/test-utils';
import { getFindRequest, getFindResponse, getRandomPlaces } from '../../../src/components/Trip/Itinerary/FindPlace';
import{ VALID_FIND_REQUEST, VALID_FIND_RESPONSE} from '../../sharedMocks';
import {sendAPIRequest} from '../../../src/utils/restfulAPI'; 
import serverSettings from '../../../src/utils/serverSettings';

describe('AddPlace', () => {
	const placeObj = {
		latLng: '40.57, -105.085',
		name: 'Colorado State University, South College Avenue, Fort Collins, Larimer County, Colorado, 80525-1725, United States',
	};

	const placeObj2 = {
		latLng: '1,1',
		name: 'Unkown',
	};

	const props = {
		toggleAddPlace: jest.fn(),
		append: jest.fn(),
		isOpen: true,
	};

	beforeEach(() => {
		render(
			<AddPlace
				append={props.append}
				isOpen={props.isOpen}
				toggleAddPlace={props.toggleAddPlace}
			/>
		);
	});

    test('base: validates input', async () => {
		const coordInput = screen.getByTestId('coord-input');
		
		act (() => { 
			user.type(coordInput, placeObj.latLng);
		});

		await waitFor(() => {
			expect(coordInput.value).toEqual("5");
		});
	}); 

	test('base: handles invalid input', async () => {
		const coordInput = screen.getByTestId('coord-input');
		
		act (() => { 
			user.paste(coordInput, '1');
		});

		await waitFor(() => {
			expect(coordInput.value).toEqual('1');
		});
	});

	test('xander3: Must Be Address', async () => {
		let places = []; 
		
		places.push({name: "test 1"});
		
		let newFoundPlaces = []
		await act (async () => { 
			newFoundPlaces = await mustBeAddress("test 1", places);
		});
		
		expect(newFoundPlaces.length).toEqual(0);
	}); 

	test('xander3: New Found Places Json', async () => {
		let foundPlacesJson = []; 
		let places = [];
		foundPlacesJson.push({name: "test 1"});

		let newFoundPlaces = []
		act (() => { 
			newFoundPlaces = newFoundPlacesJson(foundPlacesJson, places);
		});
 
		expect(newFoundPlaces.length).toEqual(1);
	}); 

	test('xander3: Get New Found Places', async () => {
		let places = [];
		let newFoundPlaces = []
		await act (async () => { 
			newFoundPlaces = await getNewFoundPlaces("40,40", places);
		});

		expect(newFoundPlaces.length).toEqual(0);
	}); 

	test('xander3: Test Lat Lng', async () => {

		expect(isLatLngValid(undefined, undefined)).toEqual(false);
		expect(isLatLngValid(1, 1)).toEqual(true);
	}); 

	test('xander3: get find request test', async () => {
		expect(JSON.stringify(getFindRequest("apple"))).toEqual(VALID_FIND_REQUEST);
	}); 

	test('xander3: test find response', async () => {
		fetch.mockResponse(VALID_FIND_RESPONSE);
        const response = await sendAPIRequest(JSON.parse(VALID_FIND_RESPONSE), 'http://localhost:31400');
        expect(response).toEqual(JSON.parse(VALID_FIND_RESPONSE));
		serverSettings.activeServerUrl = 'http://localhost:31400';
		getFindResponse("apple",JSON.parse(VALID_FIND_RESPONSE));
	}); 

	test('xander3: test find response', async () => {
		serverSettings.activeServerUrl = 'http://localhost:31400';
        getRandomPlaces();
	}); 

});

