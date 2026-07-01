import styles from './stylesheets/DeviceGrid.module.css';
import DeviceCard from "./DeviceCard.jsx";
import {useState} from "react";
import coreConstants from "../CoreConstants.jsx";
import useFetch from "../hooks/useFetch.jsx";
import useFetchPolling from "../hooks/useFetchPolling.jsx";

function DeviceGrid() {

    const [devices, setDevices] = useState([]);

    const errorMessage = "Error fetching devices";
    const deviceFetchDelayMillis = 5000;

    useFetch(coreConstants.DEVICES_URL, setDevices, errorMessage);
    useFetchPolling(coreConstants.DEVICES_URL, setDevices, devices, errorMessage, deviceFetchDelayMillis);

    return (
        <div className={styles.deviceGrid}>
            {devices.map(device =>
                <DeviceCard name={device.name} isOnline={device.online} isArmed={device.armed} id = {device.id} key={device.id} />
            )}
        </div>
    )
}

export default DeviceGrid;