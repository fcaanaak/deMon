import styles from './stylesheets/DeviceCard.module.css';
import coreConstants from "../CoreConstants.jsx";

function DeviceCard({name, isOnline}) {

    const statusImg = isOnline ? coreConstants.CHECK_MARK_IMG_PATH : coreConstants.CROSS_MARK_IMG_PATH;

    return (
        <div className={styles.deviceCard}>
            <img src = {statusImg} alt={"Device Online"}/>
            <div>
                <p>Device Name: <b>{`\n${name}`}</b></p>
                <p>Status: <b>{isOnline ? "Online": "Offline"}</b></p>
            </div>
        </div>
    )
}

export default DeviceCard;