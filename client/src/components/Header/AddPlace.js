import React, { useEffect, useState } from 'react';
import {
	Button,
	Col,
	Modal,
	ModalBody,
	ModalHeader,
	Input,
	ModalFooter,
} from 'reactstrap';

import Coordinates from 'coordinate-parser';
import { reverseGeocode } from '../../utils/reverseGeocode';
import { getPlaces, getRandomPlaces } from '../../components/Trip/Itinerary/FindPlace';
import { Place } from '../../models/place.model';
import { GiCondorEmblem } from 'react-icons/gi';
import { usePlaces } from '../../hooks/usePlaces';
import { act } from 'react-dom/test-utils';
export default function AddPlace(props) {
	const { places } = usePlaces();


	const [foundPlaces, setFoundPlaces] = useState([]);
	const [coordString, setCoordString] = useState('');
	return (
		<Modal isOpen={props.isOpen} toggle={props.toggleAddPlace}>
			<AddPlaceHeader toggleAddPlace={props.toggleAddPlace} />
			<PlaceSearch
				append={props.append}
				foundPlaces={foundPlaces}
				setFoundPlaces={setFoundPlaces}
				coordString={coordString}
				setCoordString={setCoordString}
				places={places}
			/>
			<AddPlaceFooter
				foundPlaces={foundPlaces}
				setFoundPlaces={setFoundPlaces}
				setCoordString={setCoordString}
				places={places}
			/>
		</Modal>
	);
}

function AddPlaceHeader(props) {
	return (
		<ModalHeader className='ml-2' toggle={props.toggleAddPlace}>
			Add a Place
		</ModalHeader>
	);
}

function RandomPlaceButton(props){

	const setRandomPlaces = async () => {
		let jsonPlaces = await getRandomPlaces();
		let places = newFoundPlacesJson(jsonPlaces, props.places)
		console.log("Random Places Length: "+places.length);
		props.setFoundPlaces(places);
	};

	
    return(
        <Button color="primary"
		data-testid='random-place-button'
            onClick={() =>{
				setRandomPlaces();
			}}
        >
            Add Random Place  
        </Button>
    )
}


function PlaceSearch(props) {
	useEffect(() => {
		verifyCoordinates(props.coordString, props.setFoundPlaces, props.places);
	}, [props.coordString]);

	return (
		<ModalBody>
			<Col>
				<Input
					onChange={(input) => props.setCoordString(input.target.value)}
					placeholder='Search for Place'
					data-testid='coord-input'
					value={props.coordString}
				/>
				<PlacesInfo foundPlaces={props.foundPlaces} setCoordString={props.setCoordString} coordString={props.coordString} append={props.append} />
			</Col>
		</ModalBody>
	);
}

function PlacesInfo(props) {
	if (props.foundPlaces.length == undefined) { return (<></>); }
	let placesList = []
	props.foundPlaces.forEach((place, index) => {
		placesList.push(
			<div key={index+1}>
				<h6 key={index}>{place.formatPlaceShort()}</h6>
				<Button
					{...props}
					color='primary'
					onClick={() => {
						PlaceInfoButton(props, index);
					}}
					data-testid='add-place-button' disabled={!props.foundPlaces} key={index+2}
				>	Add Place
				</Button>
			</div>
		)})
	return (
		<>	<br></br>
			<div>
				{placesList}
			</div>		</>
	);
}

function PlaceInfoButton(props, index){
	props.append(props.foundPlaces[index]);
	let coordString = props.coordString;
	props.setCoordString('');
	props.setCoordString(coordString);
}

function AddPlaceFooter(props) {

	return (
		<ModalFooter>
			<RandomPlaceButton
				{...props}
        	/>
		</ModalFooter>
	);
}

async function verifyCoordinates(coordString, setFoundPlaces, places) {
	let newFoundPlaces = await getNewFoundPlaces(coordString, places);
	updateFoundPlaces(newFoundPlaces, setFoundPlaces);
}

export async function getNewFoundPlaces(coordString, places) {
	let newFoundPlaces = [];

	try {
		const latLngPlace = new Coordinates(coordString);
		const lat = latLngPlace.getLatitude();
		const lng = latLngPlace.getLongitude();
		if (isLatLngValid(lat, lng)) {
			const fullPlace = await reverseGeocode({ lat, lng });
			newFoundPlaces.push(fullPlace);
		}
	} catch (error) {
		newFoundPlaces = await mustBeAddress(coordString, places);
	}
	return newFoundPlaces;
}

export function isLatLngValid(lat, lng) {
	return (lat !== undefined && lng !== undefined);
}

export async function mustBeAddress(destinationString, places) {
	let newFoundPlaces = [];
	if (destinationString.length >= 3) {
		let foundPlacesJson = await getPlaces(destinationString);
		newFoundPlaces = newFoundPlacesJson(foundPlacesJson, places);
	}
	return newFoundPlaces;
}

export function newFoundPlacesJson(foundPlacesJson, places) {
	let newFoundPlaces = []
	if (foundPlacesJson != undefined) {
		foundPlacesJson.forEach(place => {
			if(!places.includes(new Place(place))) {
				newFoundPlaces.push(new Place(place));
			}
		});
	}
	return newFoundPlaces;
}

function updateFoundPlaces(newFoundPlaces, setFoundPlaces) {
	act(() => {
		if(setFoundPlaces != undefined ) {
			setFoundPlaces(newFoundPlaces);
		}
	});

}