import React, { useState } from 'react';
import {useToggle} from '../../hooks/useToggle';
import { Col, Container, Row, Button } from 'reactstrap';
import Map from './Map/Map';
import Itinerary from './Itinerary/Itinerary';
import TourPopup from '../Tour/TourPopup';
import UnitsPopup from '../Trip/Map/UnitsPopup';
import { BsPersonPlus } from 'react-icons/bs';
import { getOriginalServerUrl, sendAPIRequest } from '../../utils/restfulAPI';
import serverSettings from '../../utils/serverSettings';
import Coordinates from 'coordinate-parser';
import { reverseGeocode } from '../../utils/reverseGeocode';
import { Dropdown, DropdownMenu, DropdownToggle, DropdownItem } from 'reactstrap';
import {updateDistanceUnits} from './Map/updateDistances'

export default function Planner(props) {
	const [optimizeMenu, toggleOptimizeMenu] = useToggle(false);
	const [unitsMenu, toggleUnitsMenu] = useToggle(false);
	const [tempPlaces, setTempPlaces] = useState(null);
	return (
		<React.Fragment>
		<Container>
			<Section>
				<Map {...props} />
			</Section>
			<Section>
				<Row> <Col> <Row> <Col>
					<OptimizeButton
						toggleOptimizeMenu={toggleOptimizeMenu} places={props.places}
						tempPlaces = {tempPlaces} setTempPlaces = {setTempPlaces}
					/>
				</Col> <Col> </Col> </Row> </Col> <Col>
					<TotalDistance {...props} />
				</Col> <Col> <Row> <Col> </Col><Col> </Col><Col> </Col> <Col>
					<UnitsDropdown places={props.places}/>
				</Col> </Row> </Col> </Row>
			</Section>
			<Section>
				<Itinerary {...props} />
			</Section>
		</Container>
		<TourPopupModal
			places={props.places} placeActions={props.placeActions}
			optimizeMenu={optimizeMenu} toggleOptimizeMenu={toggleOptimizeMenu}
			tempPlaces = {tempPlaces} />
		</React.Fragment>
	);
}

function Section(props) {
	return (
		<Row>
			<Col sm={12}>{props.children}</Col>
		</Row>
	);
}

function TotalDistance(props){
	return(
		<div data-testid='Total Distance' id="distances-header">
			<Col>{`Total Trip Distance: ${0}`}</Col>
		</div>
	);
}

function OptimizeButton(props){
	return (
			<Button
				data-testid='optimization-button'
				color='primary'
				
				onClick={() => {
					props.toggleOptimizeMenu()
					setColumnText(props)}}>
				Optimize
			</Button>
		);
}
function UnitsDropdown(props){
	const [unitsIsOpen, setUnitsIsOpen] = useToggle(false);
    return(
        <div>
            <Row>
                <Dropdown isOpen={unitsIsOpen} toggle={setUnitsIsOpen}>
                <DropdownToggle color='primary' caret >
                        Units
                    </DropdownToggle>
                    <DropdownMenu>
                        <DropdownItem onClick={() => updateDistanceUnits("miles", props.places)}>Miles</DropdownItem>
                        <DropdownItem onClick={() => updateDistanceUnits("kilometers", props.places)}>Kilometers</DropdownItem>
                        <DropdownItem onClick={() => updateDistanceUnits("meters", props.places)}>Meters</DropdownItem>
                        <DropdownItem onClick={() => updateDistanceUnits("nautical miles", props.places)}>Nautical Miles</DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </Row>
        </div>
    );
}

function TourPopupModal(props){
	return (
		<>
		<TourPopup
				places={props.places}
				placeActions={props.placeActions}
				isOpen={props.optimizeMenu}
				toggleOptimizeMenu={props.toggleOptimizeMenu}
				tempPlaces = {props.tempPlaces}
			/>
		</>);
}


async function setColumnText(props){
    
	if(props.places != null){
		let tempPlaces = await getNewPlaces(props.places);

        props.setTempPlaces(tempPlaces);
    	const col1 = document.getElementById("Temp1");
		const col2 = document.getElementById("Temp2");

		if(col1 != null){
		col1.innerText = generatePlacesList(props.places) }
		
		if(col2!=null){
		col2.innerText = generatePlacesList(tempPlaces)
		}
    }
    
}
async function getNewPlaces(places){
    let response = await getTourResponse(places)
    if(response.places!=null){
        for(let i=0; i<response.places.length; i++){
         response.places[i] = await remakePlaces(response.places[i].id, places);
        }
    }
    return response.places;
}

async function remakePlaces(newPlaceIndex, places){
    try {
		let index =  parseInt(newPlaceIndex);
		const fullPlace = places[index];
        return fullPlace;
	} catch (error) {
        return coordSet

	}
}
export function putAllOfThePlacesIntoJSON(places, request){
    for(let i = 0; i < places.length; i++) {
		let index = i.toString();
        request["places"].push({latitude: places[i].latitude, longitude: places[i].longitude, id: index});
    }
    return request; 
}

function generatePlacesList(places){
    let placesList = "Empty";
    if(places != null){
        placesList = "";
        for(let i = 0; i < places.length; i++){
            placesList += i + ": " + places[i].defaultDisplayName + " ";
        }
    }
    return placesList;
}




export async function getTourResponse(places) {
    
    let request = getTourRequest(places);

    const response = await sendAPIRequest(request, serverSettings.activeServerUrl);

    return response;
}

export function getTourRequest(places) {
    let request = {
        requestType: "tour",
        earthRadius: 3959.0,
        response: 1,
        places: []      
    }

    return putAllOfThePlacesIntoJSON(places, request);
}



export function temporaryPlaceJSONCreator (name, latitude, longitude) {
    return  JSON.stringify({"name" : name, "latitude": latitude, "longitude": longitude}) ;
}