import React from "react";
import { ModalHeader, Modal, ModalFooter, Button, Row, Col } from "reactstrap";

export default function TourPopup(props){
    return(
        <Modal data-testid="Tour Popup" isOpen={props.isOpen} toggle={props.toggleOptimizeMenu}> 
            <TourPopupHeader toggleOptimizeMenu={props.toggleOptimizeMenu}/>
            <TourPopupBody places = {props.places}  />
            <TourPopupFooter tempPlaces={props.tempPlaces} toggleOptimizeMenu={props.toggleOptimizeMenu} placeActions={props.placeActions}/>
        </Modal>
    );
}

function TourPopupHeader(props){
    return(
        <ModalHeader className='ml-2' toggle={props.toggleOptimizeMenu}>
            Would you like to optimize your trip?
        </ModalHeader>
    );
} 

function TourPopupBody(){
    
    return(
        <div>
            <Row>
            <Col className="ml-3">
                    Current Trip
            </Col>
            <Col className="ml-3">
                New Trip
            </Col>
            </Row>
            <Row>
                <Col className="ml-3">
                    <p id = "Temp1"> Loading</p>
                </Col>
                <Col className="ml-3">
                    <p id = "Temp2"> Please Wait</p>
                </Col>
            </Row>
        </div> 
    );
}


function TourPopupFooter(props){
    return(
        <ModalFooter>
            <CancelOptimization toggleOptimizeMenu={props.toggleOptimizeMenu}>  
            </CancelOptimization>
            <ConfirmOptimization toggleOptimizeMenu={props.toggleOptimizeMenu} setPlaces = {props.placeActions.setPlaces} tempPlaces={props.tempPlaces}>  
            </ConfirmOptimization>
        </ModalFooter>
    );
}

function CancelOptimization(props){
    return(
        <Button data-testid='cancel-optimization-button'
        color='secondary'
        onClick={() => props.toggleOptimizeMenu()}>
            Cancel
        </Button>
    );
}

function ConfirmOptimization(props){
    return(
        <Button data-testid='confirm-optimization-button'
        color='primary'
        onClick={() =>{
            props.toggleOptimizeMenu() 
            props.setPlaces(props.tempPlaces)
        } }>
            Confirm
        </Button>
    );
}