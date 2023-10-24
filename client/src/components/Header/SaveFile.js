import React, { useState } from "react";
import { Button, Modal, ModalHeader, ModalFooter, ModalBody } from "reactstrap";
import { SavePlaces } from "../../utils/saveTrip";
import {usePlaces} from "../../hooks/usePlaces"
import { FaCheck, FaTimes } from 'react-icons/fa';

export default function SaveFile(props) {
    const [disallowSave, setDisallowSave] = useState(false);
    const [showValidityIcon, setShowValidityIcon] = useState(false);
    
    function clear(){
        props.toggleSaveFile();
        setShowValidityIcon(false);
        setDisallowSave(false);
    }
    
    return (
            <Modal isOpen={props.isOpen} toggle={clear}>
                <SaveFileHeader 
                    toggleSaveFile={clear}
                />
                <SaveFileBody
                    disallowSave={disallowSave}
                    setDisallowSave={setDisallowSave}
                    showValidityIcon={showValidityIcon}
                    setShowValidityIcon={setShowValidityIcon}
                    placeActions={props.placeActions}
                    clear={clear}
                    setTripName={props.setTripName}
                    places={props.places}
                />
            </Modal>
    );
}

function SaveFileHeader(props) {
    return (
        <ModalHeader className="ml-2" toggle={props.toggleSaveFile}>
           Save Trip
        </ModalHeader>
    );
}
function SaveFileBody(props) {
    const [savedFileName, setSavedFileName] = useState(null);   
    return(
        <div>
            <SaveFileFooter
            placeActions={props.placeActions} 
            
            places={props.places}
            disallowSave={props.disallowSave}
            clear={props.clear}
            setTripName={props.setTripName}
            savedFileName={savedFileName}
            />
        </div>
    );
}

function SaveFileFooter(props) {

    return (
      <ModalFooter>
        <SaveButton
            disallowSave={props.disallowSave}
            clear={props.clear}
            places={props.places}
            //setPlaces={props.placeActions.setPlaces}
            //setSavePlace={props.setSavePlace}
            
            setTripName={props.setTripName}
            savedFileName={props.savedFileName}
        />
        <CancelSaveButton
           // setSavePlace={props.setSavePlace}
            clear={props.clear}
        />
      </ModalFooter>
    );
}

function SaveButton(props) {
    let placesObject  =  {
        "earthRadius"   : 3959.0,
        "units"         : "mi",
        "places"        : props.places};
    let placesString = JSON.stringify(placesObject);
    return (
        <Button color="primary"
            disabled={props.disallowSave}
            onClick={() => {
                SavePlaces("MyTrip.json", "application/jsontext/plain", placesString);
                props.clear();
            }} 
            data-testid='confirm-save-button'
        >
            Save File 
        </Button>
    )
}

function CancelSaveButton(props){
    return(
        <Button color="secondary" 
            onClick={() =>{
                props.clear();
                }
            }
            data-testid='close-save'
        >
            Cancel 
        </Button>
    )
}