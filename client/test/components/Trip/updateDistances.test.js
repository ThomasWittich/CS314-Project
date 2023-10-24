import { render } from '@testing-library/react';
import React from 'react';
import { updateDistances } from "../../../src/components/Trip/Map/updateDistances";
import Planner from '../../../src/components/Trip/Planner';
import { beforeEach, describe, test, jest, expect } from '@jest/globals';


describe('updateDistance', () => {
	const plannerProps = {
		createSnackBar: jest.fn(),
		places: [],
		selectedIndex: -1,
		placeActions: {
			append: jest.fn(),
		},
	};

	beforeEach(() => {
		render(<Planner {...plannerProps} />);
	});

    test('Witticht: test update distance', async () => {
        let places = [{"name":"Miami, Florida", "latitude":  "25.8",  "longitude": "80.2"},
		{"name": "Bermuda", "latitude":  "32.3", "longitude": "64.7"},
		{"name": "San Juan, Puerto Rico", "latitude":  "18.5", "longitude": "66.1"},
		{"name": "Turks and Caicos", "latitude":  "21.7", "longitude": "71.8"}];

        expect(JSON.stringify(updateDistances(places)));
	});
});