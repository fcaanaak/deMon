import styles from "./stylesheets/Dashboard.module.css"
import coreConstants from "/src/CoreConstants.jsx"

function DevicesBrief({downedDevicesCount}) {

    const downedDevicesPresent = downedDevicesCount > 0;

    const displayImage = downedDevicesPresent ? coreConstants.CROSS_MARK_IMG_PATH : coreConstants.CHECK_MARK_IMG_PATH;
    const issueStatus = downedDevicesPresent ? `${downedDevicesCount} device${downedDevicesCount === 1 ? "" : "s"} offline` : "No issues";

    return (
        <div className={`${styles.split} ${styles.devicesBrief}`}  >
            <div className={styles.centered}>
                <div className={styles.textContainer}>
                    <h2>Device Alerts</h2>
                    <h3 className={downedDevicesPresent ? `${styles.issueIndicatorActive}` : `${styles.issueIndicatorInactive}`}>{issueStatus}</h3>
                </div>
                <img src = {displayImage} alt={"idk"} style={{ width: "100%", scale:0.6}} />
            </div>
        </div>
    )
}

export default DevicesBrief;