import React, {useState} from "react";
import {updateDistanceUnits} from './updateDistances'
import { ModalHeader, Modal, ModalFooter, Button, Row, Col } from "reactstrap";

export default function UnitsPopup(props){
    return(
        <Modal data-testid="Units Popup" isOpen={props.isOpen} toggle={props.toggleUnitsMenu}> 
            <UnitsPopupHeader toggleUnitsMenu={props.toggleUnitsMenu}/>
            <UnitsPopupBody />
            <UnitsPopupFooter toggleUnitsMenu={props.toggleUnitsMenu} />
        </Modal>
    );
}

function UnitsPopupHeader(props){
    return(
        <ModalHeader className='ml-2' toggle={props.toggleOptimizeMenu}>
            Change Distance Units
        </ModalHeader>
    );
} 

function UnitsPopupBody(){
    const [unitTag, setUnitTag] = useState("miles");
    return(
        <div>
            <Row> <Col>
                Select Units
            </Col>
            <Col>
                <select value={unitTag} onChange={e => 
                    {setUnitTag(e.target.value)
                    updateDistanceUnits(e.target.value)}}>
                    <option value="miles">Miles</option>
                    <option value="kilometers">Kilometers</option>
                    <option value="meters">Meters</option>
                    <option value="nautical miles">Nautical Miles</option>
                </select>
            </Col>
            </Row>
        </div> 
    );
}


function UnitsPopupFooter(props){
    return(
        <ModalFooter>
            <CancelButton toggleUnitsMenu={props.toggleUnitsMenu}>  
            </CancelButton>
            <ChangeDistances toggleUnitsMenu={props.toggleUnitsMenu}>  
            </ChangeDistances>
        </ModalFooter>
    );
}

function CancelButton(props){
    return(
        <Button data-testid='cancel-Units-button'
        color='secondary'
        onClick={() => props.toggleUnitsMenu()}>
            Cancel
        </Button>
    );
}

function ChangeDistances(props){
    return(
        <Button data-testid='confirm-units-button'
        color='primary'
        onClick={() =>{
            props.toggleUnitsMenu() 
            
        } }>
            Confirm
        </Button>
    );
}