import styles from "./stylesheets/ReportsList.module.css";
import ReportsFilter from "./ReportsFilter.jsx";
import ReportCard from "./ReportCard.jsx";
import {useEffect, useRef, useState} from "react";
import coreConstants from "../CoreConstants.jsx";

function ReportsList({isBrief}) {

    const wsRef = useRef(null);
    const [reports, setReports] = useState([]);

    /**
     * Fetch all the reports from the server
     */
    function getReports() {
        fetch(coreConstants.ALL_REPORTS_URL)
            .then((response) => response.json())
            .then(respJson => {
                setReports(respJson);
            })
            .catch((error) => alert(error));
    }

    /**
     * Set up the callbacks used by the websocket client
     * @param socket An already created websocket
     */
    function setupWsCallbacks(socket) {
        socket.onmessage = (event) => {
            const newReport = JSON.parse(event.data);
            setReports(prevState => [newReport, ...prevState]);
        }

        socket.onerror = (err) => {
            alert("Websocket Error: " + err);
        }
    }

    function wsInit() {
        const socket = new WebSocket(coreConstants.NEW_REPORTS_URL);
        wsRef.current = socket;

        setupWsCallbacks(socket);

        return () => {
            socket.close(1000, "Component Unmounted");
        }
    }

    useEffect(() => {
        getReports();
        return wsInit();
    }, []);

    function handleFilterUpdate(filterData) {
        setReports(filterData);
    }

    function renderReports() {
        const reportsPreviewNum = 4;
        let renderedReportsList = reports;

        if (isBrief) {
            renderedReportsList = reports.slice(0, reportsPreviewNum);
        }

        return (
            <ul className={styles.reportsList}>
                {renderedReportsList.map(report =>
                    <ReportCard name={report.deviceName} detectionDateTime={report.detectionDateTime}/>
                )}
            </ul>
        )
    }

    return (
        <div>
            {!isBrief && <ReportsFilter reportsList={reports} filterUpdateCallback={handleFilterUpdate} />}
            {renderReports()}
        </div>
    )

}

export default ReportsList;