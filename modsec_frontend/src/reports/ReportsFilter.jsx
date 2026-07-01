import {useState} from "react"
import styles from "./stylesheets/ReportsFilter.module.css"

function ReportsFilter({reportsList, filterUpdateCallback}) {

    const [nameQuery, setNameQuery] = useState("");
    const [backupReport, setBackupReport] = useState([]);
    const [isFiltering, setIsFiltering] = useState(false);

    function isFilterValEmpty(filterValue) {
        return filterValue === "" || filterValue === null;
    }

    function resetFilter() {
        filterUpdateCallback(backupReport);
        setIsFiltering(false);
    }

    function filterInit() {
        setBackupReport(reportsList);
        setIsFiltering(true);
    }


    function handleNewFilterVal(filterVal) {

        if (isFilterValEmpty(filterVal)) {
            resetFilter();
        } else {

            if (!isFiltering) {
                filterInit();
            }

            filterUpdateCallback(backupReport.filter(report => report.deviceName.startsWith(filterVal)));
        }

    }

    /**
     * A function to be called when we enter text into the filter text entry
     * @param e
     */
    function handleNameQueryChange(e) {
        const filterVal = e.target.value;
        setNameQuery(filterVal);

        handleNewFilterVal(filterVal);
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