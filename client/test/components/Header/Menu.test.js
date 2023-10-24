import React from 'react';
import { beforeEach, describe, expect, test, jest } from '@jest/globals';
import user from '@testing-library/user-event';
import { render, screen, waitFor } from '@testing-library/react';
import Menu from '../../../src/components/Header/Menu';
import { DEFAULT_STARTING_POSITION } from '../../../src/utils/constants';

describe('Menu', () => {
	const menuProps = {
		toggleAbout: jest.fn(),
		placeActions: {
			append: jest.fn(),
			removeAll: jest.fn(),
		},
		toggleAddPlace: jest.fn(),
		disableRemoveAll: false,
		toggleServerSettings: jest.fn(),
	};

	beforeEach(() => {
		render(<Menu {...menuProps} />);
	});

	test('base: Opens the menu', () => {
		const menuToggle = screen.getByTestId('menu-toggle');
		user.click(menuToggle);
		expect(screen.getByTestId('menu-button-container')).toBeTruthy();
	});

	test('base: Toggles About', async () => {
		const menuToggle = screen.getByTestId('menu-toggle');
		await waitFor(() => user.click(menuToggle));
		const aboutButton = screen.getByTestId('about-button');
		user.click(aboutButton);
		expect(menuProps.toggleAbout).toHaveBeenCalled();
	});

	test('base: Adds home location', async () => {
		const menuToggle = screen.getByTestId('menu-toggle');
		await waitFor(() => user.click(menuToggle));
		const addHomeButton = screen.getByTestId('home-button');
		user.click(addHomeButton);
		expect(menuProps.placeActions.append).toHaveBeenCalledWith(
			DEFAULT_STARTING_POSITION
		);
	});

	test('base: Removes all places', async () => {
		const menuToggle = screen.getByTestId('menu-toggle');
		await waitFor(() => user.click(menuToggle));
		const removeAllButton = screen.getByTestId('remove-all-button');
		user.click(removeAllButton);
		expect(menuProps.placeActions.removeAll).toHaveBeenCalled();
	});

	// test('base: Toggles Server Settings', async () => {
	// 	const menuToggle = screen.getByTestId('menu-toggle');
	// 	await waitFor(() => user.click(menuToggle));
	// 	const serverSettingsButton = screen.getByTestId('server-settings-button');
	// 	user.click(serverSettingsButton);
	// 	expect(menuProps.toggleServerSettings).toHaveBeenCalled();
	// });
});
