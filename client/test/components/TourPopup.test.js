import React from 'react';
import { describe, expect, test, jest } from "@jest/globals";
import TourPopup from '../../src/components/Tour/TourPopup';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

describe('TourPopup' , () =>{
    const props = {
        toggleOptimizeMenu: jest.fn(),
        places: [],
        placeActions: {
        setPlaces: jest.fn(),
        },
        tempPlaces: {
            setTempPlaces: jest.fn(),
        },
    }

    beforeEach(() => {
        render(<TourPopup 
            places={props.places}
            placeActions={props.placeActions}
            isOpen={true}
            toggleOptimizeMenu={props.toggleOptimizeMenu}
            tempPlaces = {props.tempPlaces}
            />
        );
	});

    test('milkjug: renders when toggles', ()=>{
        screen.getByText("Would you like to optimize your trip?");
    })

    test('milkjug: sets tour to optimized tour on button press', async ()=>{
        const confirmButton = screen.getByTestId('confirm-optimization-button');
        userEvent.click(confirmButton);
        expect(props.toggleOptimizeMenu()).toBeCalled;
        expect(props.placeActions.setPlaces(props.tempPlaces)).toBeCalled;
    })

    test('milkjug: closes when button is pressed', async ()=>{
        const cancelButton = screen.getByTestId('cancel-optimization-button');
        userEvent.click(cancelButton);
        expect(props.toggleOptimizeMenu()).toBeCalled;
    })
});