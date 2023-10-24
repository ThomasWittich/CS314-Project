var latType = '';
var lngType = '';

export function latLngToText(latLng, precision = 2) {
	const lat = latLng?.lat ?? latLng?.latitude;
	const lng = latLng?.lng ?? latLng?.longitude;
    return latLng && lat !== undefined && lng !== undefined ? `${lat.toFixed(precision)}°${evaluateAll(lat, "lat")}, ${lng.toFixed(precision)}°${evaluateAll(lng, "lng")}` : "";
}

export function placeToLatLng(place) {
    return place && place.latitude !== undefined && place.longitude !== undefined ? { lat: parseFloat(place.latitude), lng: parseFloat(place.longitude) } : place;
}

export function latLngToPlace(latLng) {
    return latLng && latLng.lat !== undefined && latLng.lng !== undefined ? { latitude: latLng.lat.toString(), longitude: latLng.lng.toString() } : latLng;
}

function evaluateAll(val, type){
	if (val > 0) {
		latType='N';
		lngType='E';
	} 
	else if (val < 0) {
		latType='S';
		lngType='W';
	} else {
		latType='';
		lngType='';
	}
	if(type == "lat"){
		return latType;
	}
	return lngType;
}