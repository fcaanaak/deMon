import styles from "./stylesheets/ReportCard.module.css"

function ReportCard ({name, detectionDateTime}) {

    return (

        <li className={styles.card}>
            <p><b>Device Name: </b> {name}</p>
            <p><b>Detection Date and Time:</b> {detectionDateTime}</p>
        </li>

    )

}

export default ReportCard;