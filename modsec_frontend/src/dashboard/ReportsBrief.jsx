import styles from "./stylesheets/Dashboard.module.css"
import ReportsList from "../reports/ReportsList.jsx";

function ReportsBrief() {
    return (
        <div className={`${styles.split} ${styles.reportsBrief}`}>
            <div>
                <div className={styles.textContainer}>
                    <h2>Latest Reports</h2>
                </div>
            </div>
            <ReportsList isBrief={true} />
        </div>
    )
}

export default ReportsBrief