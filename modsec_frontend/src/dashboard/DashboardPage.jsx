import DevicesBrief from "./DevicesBrief.jsx";
import ReportsBrief from "./ReportsBrief.jsx";
import coreConstants from "../CoreConstants.jsx";
import {useState} from "react";
import useFetch from "../hooks/useFetch.jsx";
import useFetchPolling from "../hooks/useFetchPolling.jsx";

function DashboardPage() {
    const [downedDevices, setDownedDevices] = useState(0);

    const downedDeviceFetchDelayMillis = 5000;
    const errorMessage = "Error fetching downed devices";

    useFetch(coreConstants.DOWNED_DEVICES_URL, setDownedDevices, errorMessage);

    useFetchPolling(coreConstants.DOWNED_DEVICES_URL, setDownedDevices,
        downedDevices, errorMessage,
        downedDeviceFetchDelayMillis);

    return (
        <div>
            <DevicesBrief downedDevicesCount={downedDevices}/>
            <ReportsBrief />
        </div>
    )
}

export default DashboardPage;
