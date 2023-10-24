import React from 'react';
import { Marker as LeafletMarker, Popup } from 'react-leaflet';
import { latLngToText, placeToLatLng } from '../../../utils/transformers';
import markericon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';
import staricon from '../../../static/images/star_marker.png';
import hearticon from '../../../static/images/heart_marker.png';
import diamondicon from '../../../static/images/diamond_marker.png';
import daveicon from '../../../static/images/dave_marker.png';
import stariconred from '../../../static/images/star_marker_red.png';
import stariconblue from '../../../static/images/star_marker_blue.png';
import staricongreen from '../../../static/images/star_marker_green.png';
import hearticonred from '../../../static/images/heart_marker_red.png';
import hearticonblue from '../../../static/images/heart_marker_blue.png';
import hearticongreen from '../../../static/images/heart_marker_green.png';
import diamondiconred from '../../../static/images/diamond_marker_red.png';
import diamondiconblue from '../../../static/images/diamond_marker_blue.png';
import diamondicongreen from '../../../static/images/diamond_marker_green.png';

const MARKER_ICON = L.icon({ iconUrl: markericon, shadowUrl: iconShadow, iconAnchor: [12, 40] });
const STAR_ICON = L.icon({ iconUrl: staricon, shadowUrl: iconShadow, iconAnchor: [12, 40] });
const DIAMOND_ICON = L.icon({ iconUrl: diamondicon, shadowUrl: iconShadow, iconAnchor: [12, 40] });
const HEART_ICON = L.icon({ iconUrl: hearticon, shadowUrl: iconShadow, iconAnchor: [12, 40] });
const DAVE_ICON = L.icon({ iconUrl: daveicon, shadowUrl: iconShadow, iconAnchor: [12, 40] });

const STAR_ICON_RED = L.icon({ iconUrl: stariconred, shadowUrl: iconShadow, iconAnchor: [12, 40] });
const STAR_ICON_BLUE = L.icon({ iconUrl: stariconblue, shadowUrl: iconShadow, iconAnchor: [12, 40] });
const STAR_ICON_GREEN = L.icon({ iconUrl: staricongreen, shadowUrl: iconShadow, iconAnchor: [12, 40] });

const HEART_ICON_RED = L.icon({ iconUrl: hearticonred, shadowUrl: iconShadow, iconAnchor: [12, 40] });
const HEART_ICON_BLUE = L.icon({ iconUrl: hearticonblue, shadowUrl: iconShadow, iconAnchor: [12, 40] });
const HEART_ICON_GREEN = L.icon({ iconUrl: hearticongreen, shadowUrl: iconShadow, iconAnchor: [12, 40] });

const DIAMOND_ICON_RED = L.icon({ iconUrl: diamondiconred, shadowUrl: iconShadow, iconAnchor: [12, 40] });
const DIAMOND_ICON_BLUE = L.icon({ iconUrl: diamondiconblue, shadowUrl: iconShadow, iconAnchor: [12, 40] });
const DIAMOND_ICON_GREEN = L.icon({ iconUrl: diamondicongreen, shadowUrl: iconShadow, iconAnchor: [12, 40] });

var icon = MARKER_ICON;
var marker = "pin";
var color = "black";

export default function Marker(props) {
    function showMarkerPopup(ref) {
        if (ref) {
            ref.leafletElement.openPopup();
        }
    }

    if (!props.place) {
        return null;
    }

    return (
        <LeafletMarker ref={(ref) => showMarkerPopup(ref)} position={placeToLatLng(props.place)} icon={icon}>
            <Popup offset={[0, -18]} className="font-weight-bold">
                {props.place.formatPlace()}
                <br/>
                <small className="text-muted">
                    {latLngToText(placeToLatLng(props.place), 6)}
                </small>
            </Popup>
        </LeafletMarker>
    );
}

export function SetIcon(markerTag){
    marker = markerTag;
    if (markerTag === "star"){
        starColor(color);
    }
    else if (markerTag === "heart"){
        heartColor(color);
    }
    else if (markerTag === "diamond"){
        diamondColor(color);
    }
    else if (markerTag === "dave"){
        icon = DAVE_ICON;
    }
    else{
        icon = MARKER_ICON;
    }
}

export function starColor(color) {
    console.log("Color: " + color);

    switch(color){
        case "red":
            icon = STAR_ICON_RED;
            break;
        case "blue":
            icon = STAR_ICON_BLUE;
            break;
        case "green":
            icon = STAR_ICON_GREEN;
            break;
        default:
            icon = STAR_ICON;
            break;
    }
}

export function heartColor(color) {
    switch(color){
        case "red":
            icon = HEART_ICON_RED;
            break;
        case "blue":
            icon = HEART_ICON_BLUE;
            break;
        case "green":
            icon = HEART_ICON_GREEN;
            break;
        default:
            icon = HEART_ICON;
            break;
    }
}

export function diamondColor(color) {
    switch(color){
        case "red":
            icon = DIAMOND_ICON_RED;
            break;
        case "blue":
            icon = DIAMOND_ICON_BLUE;
            break;
        case "green":
            icon = DIAMOND_ICON_GREEN;
            break;
        default:
            icon = DIAMOND_ICON;
            break;
    }
}

export function setStuff(newColor){
    console.log("set stuff");
    console.log("New Color: "+newColor);
    color = newColor;
    console.log("New Color Color: "+color);
    SetIcon(marker);
}

export function getColor() {
    return color;
}