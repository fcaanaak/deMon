import DevicesBrief from "./DevicesBrief.jsx";
import ReportsBrief from "./ReportsBrief.jsx";
import coreConstants from "../CoreConstants.jsx";
import {useEffect, useState} from "react";

function DashboardPage() {
    const [downedDevices, setDownedDevices] = useState(0);

    function getDownedDevices() {
        fetch(coreConstants.DOWNED_DEVICES_URL)
            .then((response) => response.json())
            .then(respJson => {
                setDownedDevices(respJson);
            })
            .catch((error) => alert(error));
    }

    useEffect(() => {
        getDownedDevices();
    }, []);

    useEffect(() => {
        setTimeout(getDownedDevices, 5000);
    }, [downedDevices]);

    return (
        <div>
            <DevicesBrief downedDevicesCount={downedDevices}/>
            <ReportsBrief />
        </div>
    )
}

export default DashboardPage;
