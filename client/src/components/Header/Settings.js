import React, {useState} from "react";
import {useToggle} from '../../hooks/useToggle';
import { ModalHeader, Modal, ModalFooter, Button, Row, Col} from "reactstrap";
import {SetIcon} from '../Trip/Map/Marker';
import {ChangeMapLayer} from '../Trip/Map/Map';
import { Dropdown, DropdownMenu, DropdownToggle, DropdownItem } from 'reactstrap';
import {setStuff} from "../Trip/Map/Marker";


//var marker = "pin";

export default function Settings(props){
    return(
        <Modal data-testid="Settings" isOpen={props.isOpen} toggle={props.toggleSettings}> 
            <SettingsHeader 
                toggleSettings={props.toggleSettings}
            />
            <SettingsBody toggleProps={props.toggleServerSettings}/>
            <SettingsFooter/>
        </Modal>
    );
}

function SettingsHeader(props){
    return(
        <ModalHeader className='ml-2' toggle={props.toggleSettings}>
            Map Details
        </ModalHeader>
    );
} 

function SettingsBody(props){
    
    return(
        <div>
            <Row>
            <Col></Col>
            <Col className="ml-2">
                <MarkerDropdown></MarkerDropdown>
            </Col>
            <Col></Col>
            <Col className="ml-2">
                <LayerDropdown></LayerDropdown>
            </Col>
            <Col></Col>
            <Col className="ml-2">
                <ColorDropdown></ColorDropdown>
            </Col>
            <Col></Col>
            </Row>
        </div> 
    );
}


function SettingsFooter(){
    return(
        <ModalFooter>
        </ModalFooter>
    );
}

// function setMarker(m) {
//     marker = m;
// }

function MarkerDropdown(){
    const [markerIsOpen, setMarkerIsOpen] = useToggle(false);
    return(
        <div>
            <Row>
                <Dropdown isOpen={markerIsOpen} toggle={setMarkerIsOpen}>
                    <DropdownToggle color='primary' caret>
                        Marker Shape
                    </DropdownToggle>
                    <DropdownMenu>
                        <DropdownItem onClick={() => SetIcon("pin")}>Pin</DropdownItem>
                        <DropdownItem onClick={() => SetIcon("star")}>Star</DropdownItem>
                        <DropdownItem onClick={() => SetIcon("diamond")}>Diamond</DropdownItem>
                        <DropdownItem onClick={() => SetIcon("heart")}>Heart</DropdownItem>
                        <DropdownItem onClick={() => SetIcon("dave")}>Dave</DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </Row>
        </div>

    );
}

function LayerDropdown(){
    const [layerIsOpen, setLayerIsOpen] = useToggle(false);
    return(
        <div>
            <Row>
                <Dropdown isOpen={layerIsOpen} toggle={setLayerIsOpen} >
                    <DropdownToggle color='primary' caret>
                        Map Layer
                    </DropdownToggle>
                    <DropdownMenu>
                        <DropdownItem onClick={() => ChangeMapLayer("Default")}>Default</DropdownItem>
                        <DropdownItem onClick={() => ChangeMapLayer("Dark")}>Dark</DropdownItem>
                        <DropdownItem onClick={() => ChangeMapLayer("Purple")}>Purple</DropdownItem>
                        <DropdownItem onClick={() => ChangeMapLayer("Fire")}>Fire</DropdownItem>
                        <DropdownItem onClick={() => ChangeMapLayer("Topographic")}>Topographic</DropdownItem>
                        <DropdownItem onClick={() => ChangeMapLayer("Watercolor")}>Watercolor</DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </Row>
        </div>
    );
}

function ColorDropdown() {
    const [colorIsOpen, setColorIsOpen] = useToggle(false);
    return(
        <div>
            <Row>
                <Dropdown isOpen={colorIsOpen} toggle={setColorIsOpen} >
                    <DropdownToggle color='primary' caret>
                        Color
                    </DropdownToggle>
                    <DropdownMenu>
                        <DropdownItem onClick={() => setStuff("black")}>Black</DropdownItem>
                        <DropdownItem onClick={() => setStuff("red")}>Red</DropdownItem>
                        <DropdownItem onClick={() => setStuff("blue")}>Blue</DropdownItem>
                        <DropdownItem onClick={() => setStuff("green")}>Green</DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </Row>
        </div>
    );

}
