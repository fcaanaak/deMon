import styles from './stylesheets/DeviceGrid.module.css';
import DeviceCard from "./DeviceCard.jsx";
import {useEffect, useState} from "react";
import coreConstants from "../CoreConstants.jsx";

function DeviceGrid() {

    const [devices, setDevices] = useState([]);
    const deviceFetchInterval = 1000 * 10;

    /**
     * Fetch all the devices from the server
     */
    function getDevices() {
        fetch(coreConstants.DEVICES_URL)
            .then((response) => response.json())
            .then(respJson => {
                setDevices(respJson);
            })
            .catch((error) => alert(error));
    }

    // Get all devices once upon loading
    useEffect(() => {
        getDevices();
    },[]);


    useEffect(() => {
        setTimeout(getDevices, deviceFetchInterval);
    },[devices]);

    return (
        <div className={styles.deviceGrid} >

            {devices.map(device =>
                <DeviceCard name={device.name} isOnline={device.online} isArmed={device.armed} />
            )}

        </div>
    )
}

export default DeviceGrid;