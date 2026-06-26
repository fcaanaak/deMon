import styles from './stylesheets/DeviceCard.module.css';
import coreConstants from "../CoreConstants.jsx";

function DeviceCard({name, isOnline, isArmed}) {

    const statusImg = isOnline ? coreConstants.CHECK_MARK_IMG_PATH : coreConstants.CROSS_MARK_IMG_PATH;

    return (
        <div className={styles.deviceCard}>
            <img src = {statusImg} alt={"Device Online"}/>
            <div>
                <p className={styles.deviceCardText}>Device Name: <b>{`\n${name}`}</b></p>
                <p className={styles.deviceCardText}>Status: <b>{isOnline ? "Online": "Offline"}</b></p>
                <p className={isArmed ? styles.armingTextArmed: styles.armingTextDisarmed}><span>{isArmed ? "Armed" : "Disarmed"}</span></p>
            </div>
        </div>
    )
}

export default DeviceCard;