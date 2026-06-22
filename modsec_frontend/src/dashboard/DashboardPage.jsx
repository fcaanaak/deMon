import DevicesBrief from "./DevicesBrief.jsx";
import ReportsBrief from "./ReportsBrief.jsx";

function DashboardPage() {
    return (
        <div>
            <DevicesBrief downedDevicesCount={0}/>
            <ReportsBrief />
        </div>
    )
}

export default DashboardPage;
