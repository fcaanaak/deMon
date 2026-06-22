import styles from './stylesheets/DeviceGrid.module.css';
import DeviceCard from "./DeviceCard.jsx";
import {useState} from "react";

function DeviceGrid() {

    const [devices, setDevices] = useState([
        {
            "name": "Kitchen Detector",
            "isOnline": true,
        },
        {
            "name": "Living Room Sound Detector",
            "isOnline": false,
        },
        {
            "name": "Bedroom Light Detector",
            "isOnline": true,
        },
        {
            "name": "Kitchen motion Detector",
            "isOnline": true,
        },
        {
            "name": "Greenhouse Temperature Sensor",
            "isOnline": true,
        },
        {
            "name": "Basement motion detector",
            "isOnline": false,
        },
    ]);


    return (
        <div className={styles.deviceGrid} >

            {devices.map(device =>
                <DeviceCard name={device.name} isOnline={device.isOnline} />
            )}

        </div>
    )
}

export default DeviceGrid;