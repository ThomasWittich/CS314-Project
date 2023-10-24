import React from 'react';
import { render, screen } from '@testing-library/react';
import user from '@testing-library/user-event';
import { expect, test, beforeEach, describe } from '@jest/globals';
import { MOCK_PLACES } from "../../sharedMocks";

import Map from '../../../src/components/Trip/Map/Map';

describe('Map', () => {
    const places = MOCK_PLACES;
    const placeActions = {
        append: jest.fn()
    };

    beforeAll(() => {
        Object.defineProperty(window, 'scrollTo', { value: () => {}, writable: true });
    });

    beforeEach(() => {
        render(<Map places={places} placeActions={placeActions} />);
    });

    test('base: appends calls append when the map is clicked', () => {
        user.click(screen.getByRole('presentation'));
        expect(placeActions.append).toHaveBeenCalled();
    });
});