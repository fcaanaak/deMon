import {useState} from "react"
import styles from "./stylesheets/ReportsFilter.module.css"

function ReportsFilter({reportsList, filterUpdateCallback}) {

    const [nameQuery, setNameQuery] = useState("");
    const [backupReport, setBackupReport] = useState([]);
    const [isFiltering, setIsFiltering] = useState(false);

    function handleNameQueryChange(e) {
        const filterVal = e.target.value;
        setNameQuery(filterVal);

        // Can refactor this into another function later
        if (filterVal === "") {
            filterUpdateCallback(backupReport);
            setIsFiltering(false);
        } else {

            if (!isFiltering) {
                setBackupReport(reportsList);
                setIsFiltering(true);
            }

            filterUpdateCallback(backupReport.filter(report => report.name.startsWith(filterVal)));
        }


    }

    return (

        <form className={styles.reportsFilter}>
            <label>
                Search by Device Name:
                <input className = {styles.reportsFilterInput} type = "text" value={nameQuery} onChange={handleNameQueryChange} />
            </label>
        </form>
    )

}

export default ReportsFilter;