import styles from "./stylesheets/ReportsList.module.css";
import ReportsFilter from "./ReportsFilter.jsx";
import ReportCard from "./ReportCard.jsx";
import {useEffect, useRef, useState} from "react";
import coreConstants from "../CoreConstants.jsx";
import useFetch from "../hooks/useFetch.jsx";

function ReportsList({isBrief}) {

    const wsRef = useRef(null);
    const [reports, setReports] = useState([]);

    const errorMessage = "Error fetching reports";
    const numReportsInBrief = 4;

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

    function handleFilterUpdate(filterData) {
        setReports(filterData);
    }

    function renderReports() {

        let renderedReportsList = reports;

        if (isBrief) {
            renderedReportsList = reports.slice(0, numReportsInBrief);
        }

        return (
            <ul className={styles.reportsList}>
                {renderedReportsList.map(report =>
                    <ReportCard name={report.deviceName} detectionDateTime={report.detectionDateTime}/>
                )}
            </ul>
        )

    }

    useEffect(() => {
        return wsInit();
    }, []);

    useFetch(coreConstants.ALL_REPORTS_URL, setReports, errorMessage);

    return (
        <div>
            {!isBrief && <ReportsFilter reportsList={reports} filterUpdateCallback={handleFilterUpdate} />}
            {renderReports()}
        </div>
    )

}

export default ReportsList;