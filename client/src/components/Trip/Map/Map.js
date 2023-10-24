import React, { useState } from 'react';
import { Map as LeafletMap, Polyline, TileLayer } from 'react-leaflet';
import Marker from './Marker';
import { placeToLatLng } from '../../../utils/transformers';
import { DEFAULT_STARTING_POSITION } from '../../../utils/constants';
import 'leaflet/dist/leaflet.css';
import { getDistanceResponse, countDistance } from './TotalDistance';
import { getOriginalServerUrl, sendAPIRequest } from '../../../utils/restfulAPI';
import { updateDistances} from './updateDistances';
const MAP_BOUNDS = [[-90, -180], [90, 180]];
let MAP_LAYER_ATTRIBUTION = "&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors";
const MAP_MIN_ZOOM = 1;
let MAP_MAX_ZOOM = 19;
let MAP_LAYER_URL = "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png";

export default function Map(props) {
    updateDistances(props.places);

    function handleMapClick(mapClickInfo) {
        props.placeActions.append(mapClickInfo.latlng);
    }


    return (
        <LeafletMap
            className="mapStyle"
            boxZoom={false}
            useFlyTo={true}
            zoom={15}
            minZoom={MAP_MIN_ZOOM}
            maxZoom={MAP_MAX_ZOOM}
            maxBounds={MAP_BOUNDS}
            center={DEFAULT_STARTING_POSITION}
            onClick={handleMapClick}
            data-testid="Map"
        >
            <TileLayer url={MAP_LAYER_URL} attribution={MAP_LAYER_ATTRIBUTION} />
            <TripLines places={props.places} />
            <PlaceMarker places={props.places} selectedIndex={props.selectedIndex} />
        </LeafletMap>
    );

    
}

function TripLines(props) {
    const pathData = computePaths(props.places);
    return pathData.map((path, index) =>
        <Polyline
            color='#105456'
            key={`${JSON.stringify(path)}-${index}`}
            positions={path}
        />
    );
}

function computePaths(places) {
    if (places.length < 2) {
        return [];
    }

    const pathPointPairs = [];
    for (let i = 0; i < places.length; i++) {
        const fromPlace = places[i];
        const toPlace = places[(i+1) % places.length];
        pathPointPairs.push([placeToLatLng(fromPlace), placeToLatLng(toPlace)]);
    }
    return pathPointPairs;
}

function PlaceMarker({places, selectedIndex}) {
    if (selectedIndex === -1) {
        return null;
    }
    return <Marker place={places[selectedIndex]} />;
}

export function ChangeWatercolor(){
    MAP_LAYER_URL = "https://stamen-tiles-{s}.a.ssl.fastly.net/watercolor/{z}/{x}/{y}.png";
    MAP_MAX_ZOOM = 16;
    MAP_LAYER_ATTRIBUTION = 'Map tiles by <a href="http://stamen.com">Stamen Design</a>, <a href="http://creativecommons.org/licenses/by/3.0">CC BY 3.0</a> &mdash; Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors';
}

export function ChangeTopographic(){
    MAP_LAYER_URL = "https://{s}.tile.opentopomap.org/{z}/{x}/{y}.png";
    MAP_MAX_ZOOM = 17;
    MAP_LAYER_ATTRIBUTION = 'Map data: &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, <a href="http://viewfinderpanoramas.org">SRTM</a> | Map style: &copy; <a href="https://opentopomap.org">OpenTopoMap</a> (<a href="https://creativecommons.org/licenses/by-sa/3.0/">CC-BY-SA</a>)';       
}

export function ChangeDark(){
    MAP_LAYER_URL = 'https://tiles.stadiamaps.com/tiles/alidade_smooth_dark/{z}/{x}/{y}{r}.png';
    MAP_MAX_ZOOM = 20;
    MAP_LAYER_ATTRIBUTION = '&copy; <a href="https://stadiamaps.com/">Stadia Maps</a>, &copy; <a href="https://openmaptiles.org/">OpenMapTiles</a> &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors';
}

export function ChangePurple(){
    MAP_LAYER_URL = 'https://{s}.tile.thunderforest.com/transport-dark/{z}/{x}/{y}.png?apikey=a8eefc3c03bd44b397656b2cd7aec70e';
    MAP_MAX_ZOOM = 22;
    MAP_LAYER_ATTRIBUTION = '&copy; <a href="http://www.thunderforest.com/">Thunderforest</a>, &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors';
}

export function ChangeFire(){
    MAP_LAYER_URL = 'https://{s}.tile.thunderforest.com/spinal-map/{z}/{x}/{y}.png?apikey=a8eefc3c03bd44b397656b2cd7aec70e'; 
    MAP_MAX_ZOOM = 22; 
    MAP_LAYER_ATTRIBUTION = '&copy; <a href="http://www.thunderforest.com/">Thunderforest</a>, &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors';
            
}

export function ChangeDefault(){
    MAP_LAYER_URL = "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png";
    MAP_MAX_ZOOM = 19;
    MAP_LAYER_ATTRIBUTION = "&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors";        
}

export function ChangeMapLayer(layerIndex){
    switch(layerIndex){
        case "Watercolor": //Watercolor
            ChangeWatercolor();
            break;
        case "Topographic": //topographic
            ChangeTopographic();
            break;
        case "Dark": //Dark
            ChangeDark();
            break;
        case "Purple": //Purple
            ChangePurple();
            break;
        case "Fire": //Fire
            ChangeFire();
            break;
        default:
            ChangeDefault();
            break;
    }
}
