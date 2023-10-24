import React from 'react';
import { render, screen } from '@testing-library/react';
import Planner from '../../../src/components/Trip/Planner';
import { beforeEach, describe, test, jest, expect } from '@jest/globals';
import userEvent from '@testing-library/user-event';

describe('Planner', () => {
	const plannerProps = {
		createSnackBar: jest.fn(),
		places: [],
		selectedIndex: -1,
		placeActions: {
			append: jest.fn(),
		},
		toggleOptimizeMenu: jest.fn(),
	};

	beforeEach(() => {
		render(<Planner {...plannerProps} />);
	});

	test('base: renders a Leaflet map', async () => {
		screen.getByText('Leaflet');
	});

	test('base: renders Total Distance', async () => {
		screen.getByTestId('Total Distance');
	}); 

	test('base: renders trip table', async () => {
		screen.getByTestId('trip-header-title');
	});
	test('milkjug: optimize button works', async () => {
		const optimizeButton = screen.getByTestId('optimization-button');
        userEvent.click(optimizeButton);
        expect(plannerProps.toggleOptimizeMenu()).toBeCalled;
	});
});
