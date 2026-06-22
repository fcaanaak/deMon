import styles from "./stylesheets/ReportsList.module.css";
import ReportsFilter from "./ReportsFilter.jsx";
import ReportCard from "./ReportCard.jsx";
import {useEffect, useState} from "react";

function ReportsList({isBrief}) {

    const [reports, setReports] = useState(null);

    useEffect(() => {
        fetch("localhost:8080/reports")
            .then((response) => {return response.json();})
            .then(jsonData => {setReports(jsonData);})
    })


    function handleFilterUpdate(filterData) {
        setReports(filterData);
    }

    function renderReports() {
        const reportsLength = reports.length;
        const reportsPreviewNum = 4;
        let renderedReportsList = reports;

        if (isBrief) {
            renderedReportsList = reports.slice(reportsLength - reportsPreviewNum, reportsLength);
        }

        return (
            <ul className={styles.reportsList}>
                {renderedReportsList.map(report =>
                    <ReportCard name={report.name} detectionDateTime={report.detectionDateTime}/>
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