import React from 'react';
import { Container, Button } from 'reactstrap';
import { CLIENT_TEAM_NAME } from '../../utils/constants';
import Menu from './Menu';
import { useToggle } from '../../hooks/useToggle';
import AddPlace from './AddPlace';
import LoadFile from './LoadFile';
import SaveFile from './SaveFile';
import ServerSettings from './ServerSettings';
import { IoMdClose } from 'react-icons/io';
import TourPopup from '../Tour/TourPopup';
import Settings from './Settings';

export default function Header(props) {
	const [showAddPlace, toggleAddPlace] = useToggle(false);
	const [showServerSettings, toggleServerSettings] = useToggle(false);
	const [showLoadFile, toggleLoadFile] = useToggle(false);
	const [showSaveFile, toggleSaveFile] = useToggle(false);
	const [showSettings, toggleSettings] = useToggle(false);

	return (
		<React.Fragment>
			<HeaderContents
				{...props}
				toggleAddPlace={toggleAddPlace} toggleServerSettings={toggleServerSettings}
				toggleLoadFile={toggleLoadFile} toggleSaveFile={toggleSaveFile}
				toggleSettings={toggleSettings}
			/>
			<AppModals
				{...props}
				showAddPlace={showAddPlace} toggleAddPlace={toggleAddPlace}
				showLoadFile={showLoadFile} toggleLoadFile={toggleLoadFile}
				showSaveFile={showSaveFile} toggleSaveFile={toggleSaveFile}
				showServerSettings={showServerSettings} toggleServerSettings={toggleServerSettings}
				showSettings={showSettings} toggleSettings={toggleSettings}
			/>
		</React.Fragment>
	);
}

function HeaderContents(props) {
	return (
		<div className='full-width header vertical-center'>
			<Container>
				<div className='header-container'>
					<h1
						className='tco-text-upper header-title'
						data-testid='header-title'
					>
						{CLIENT_TEAM_NAME}
					</h1>
					<HeaderButton {...props} />
				</div>
			</Container>
		</div>
	);
}

function HeaderButton(props) {
	return props.showAbout ? (
		<Button
			data-testid='close-about-button'
			color='primary'
			onClick={() => props.toggleAbout()}
		>
			<IoMdClose size={32} />
		</Button>
	) : (
		<Menu
			toggleAbout={props.toggleAbout}
			placeActions={props.placeActions}
			toggleAddPlace={props.toggleAddPlace}
			toggleTourPopup={props.toggleTourPopup}
			toggleLoadFile={props.toggleLoadFile}
			toggleSaveFile={props.toggleSaveFile}
			disableRemoveAll={props.disableRemoveAll}
			toggleServerSettings={props.toggleServerSettings}
			places={props.places}
			toggleSettings={props.toggleSettings}
		/>
	);
}

function AppModals(props) {
	return (
		<>
			<AddPlace
				isOpen={props.showAddPlace} toggleAddPlace={props.toggleAddPlace}
				append={props.placeActions.append}
			/>
			<ServerSettings
				isOpen={props.showServerSettings} toggleOpen={props.toggleServerSettings}
				processServerConfigSuccess={props.processServerConfigSuccess} serverSettings={props.serverSettings}
			/>
			<LoadFile
				isOpen={props.showLoadFile} toggleLoadFile={props.toggleLoadFile}
				placeActions={props.placeActions} setTripName={props.setTripName}
			/>
			<SaveFile
				isOpen={props.showSaveFile} toggleSaveFile={props.toggleSaveFile} places={props.places}	
      		/>
			<TourPopup
				isOpen={props.optimizeMenu} toggleOptimizeMenu={props.toggleOptimizeMenu}
			/>
			<Settings
				isOpen={props.showSettings} toggleSettings={props.toggleSettings}
				toggleServerSettings={props.toggleServerSettings}
			/>
		</>
	);
}

